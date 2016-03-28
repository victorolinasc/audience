package br.com.concretesolutions.audience.system;

import android.content.res.Resources;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.concretesolutions.audience.staff.Filter;
import br.com.concretesolutions.audience.system.message.PoisonPill;
import br.com.concretesolutions.audience.tragedy.TragedyException;

public final class ActorSystem {

    private static final String TAG = ActorSystem.class.getSimpleName();
    private static final int N_THREADS = 4;

    // private static final EmptyActor EMPTY_ACTOR = new EmptyActor();

    // Handlers --------------------
    private final BaseHandler[] schedulers = new BaseHandler[N_THREADS];
    private final UIInboxHandler uiScheduler = new UIInboxHandler();

    private final Map<String, Actor> pathToActorMap = new HashMap<>();
    private final Map<String, String> reboundPaths = new HashMap<>();

    // Filters
    private final Map<Class<?>, Set<Filter>> filterMap = new HashMap<>();
    private final Set<Filter> globalFilters = new HashSet<>();

    // Registry
    private final StaffRegistry registry = new StaffRegistry();

    private boolean isStopped = false;

    public ActorSystem() {

        // Initialize system
        for (int i = 0; i < N_THREADS; i++) {
            final ActorThread actorThread = new ActorThread();
            actorThread.start();
            schedulers[i] = actorThread.getMessageHandler();
        }
    }

    public StaffRegistry staffRegistry() {
        return registry;
    }

    /**
     * Shuts down the actor system.
     * <p>
     * Existing actors in the system will not be notified of this; they will just stop receiving
     * events.
     */
    public void shutdown() {

        for (BaseHandler handler : schedulers)
            handler.getLooper().quit();

        pathToActorMap.clear();

        isStopped = true;
    }

    /**
     * Retrieves an actor. The path is treated as a relative path from root and is normalized before
     * being set. If the actor corresponding to the given path doesn't exist, it will be created.
     *
     * @param path the location of the actor in the system
     * @param cls  the actor's class
     */
    public ActorRef getOrCreateActor(String path, Class<? extends Actor> cls) {

        if (isStopped)
            throw new IllegalStateException("Cannot create actors after shutdown() is called");

        final Actor actor;
        final ActorRef result;
        if (pathToActorMap.containsKey(path)) {
            actor = pathToActorMap.get(path);
            result = new ActorRefImpl(this, actor, path);
        } else {
            actor = create(cls);
            result = new ActorRefImpl(this, actor, path);
            actor.setSelf(result);
            pathToActorMap.put(path, actor);
        }
        return result;
    }

    public ActorSystem registerGlobalFilter(Filter filter) {
        globalFilters.add(filter);
        return this;
    }

    public <T> ActorSystem registerFilter(Class<T> clazz, Filter filter) {

        Set<Filter> filters = filterMap.get(clazz);

        if (filters == null) {
            filters = new HashSet<>();
            filterMap.put(clazz, filters);
        }

        filters.add(filter);
        return this;
    }

    public ActorRef getOrCreateActor(String path) {

        final Class<? extends Actor> cls = registry.lookup(path);
        // ActorRegistry.lookup(path);

        if (cls == null) {
            String msg = "No actor class was registered for the path " + path;
            throw new Resources.NotFoundException(msg);
        }

        return getOrCreateActor(path, cls);
    }

    /**
     * Stops an actor. `postStop` will be called on the Actor's thread asynchronously.
     *
     * @param target the actor to stop
     */
    public void stop(ActorRef target) {
        stop(((ActorRefImpl) target).getActor());
    }

    void rebindActivityActor(String newActivePath) {

        String newPath = newActivePath.substring(0, newActivePath.lastIndexOf('/'));

        String oldPath = null;
        for (String path : pathToActorMap.keySet())
            if (path.startsWith(newPath)) {

                if (!path.equals(newActivePath)) {
                    reboundPaths.put(path, newActivePath);
                    oldPath = path;
                    break;
                }
                return;
            }

        pathToActorMap.remove(oldPath);
    }

    void send(ActorRefImpl target, Object actorMsg, int discriminator, ActorRef sender) {

        if (handleCommonMessageSending(target, actorMsg)) return;

        send(getNextQueue(), target.getPath(), actorMsg, discriminator, sender);
    }

    void sendUI(ActorRefImpl target, Object actorMsg, int discriminator, ActorRef sender) {

        if (handleCommonMessageSending(target, actorMsg)) return;

        send(uiScheduler, target.getPath(), actorMsg, discriminator, sender);
    }

    private final Random mQueueSelectionRandom = new Random();

    private BaseHandler getNextQueue() {
        return schedulers[mQueueSelectionRandom.nextInt(N_THREADS)];
    }

    private Actor create(Class<? extends Actor> cls) {
        try {
            final Constructor ctor = cls.getConstructor();
            return (Actor) ctor.newInstance();
        } catch (NoSuchMethodException e) {
            String msg = String.format(
                    "Actor %s must have empty constructor.\n" +
                            "Note that non-static inner classes can't have an empty constructor, so " +
                            "an Actor can't currently be a non-static inner class.", cls.getName());
            throw new RuntimeException(msg, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Could not create actor", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not create actor", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not create actor", e);
        }
    }

    private void send(BaseHandler queue, String target, Object actorMsg, int discriminator, ActorRef sender) {

        final Actor actor;

        if (pathToActorMap.containsKey(target))
            actor = pathToActorMap.get(target);

        else {

            String newPath;
            if ((newPath = reboundPaths.get(target)) != null && pathToActorMap.containsKey(newPath))
                actor = pathToActorMap.get(newPath);

            else
                throw new TragedyException("Target actor not found");
        }

        final Part part = new Part(actor, actorMsg, sender);
        final Message handlerMsg = queue.obtainMessage(discriminator, part);
        final boolean proceed = executeFilter(handlerMsg, actorMsg);

        if (proceed)
            queue.sendMessage(handlerMsg);
    }

    private boolean handleCommonMessageSending(ActorRefImpl target, Object message) {

        if (isStopped)
            throw new IllegalStateException("Cannot send messages to an actor after shutdown() is " +
                    "called");

        if (target.getActor() instanceof EmptyActor) {
            Log.i(TAG, "Message sent to empty actor: " + message);
            return true;
        }

        if (message instanceof PoisonPill) {
            stop(target);
            return true;
        }
        return false;
    }

    private boolean executeFilter(Message handlerMessage, Object actorMessage) {

        if (globalFilters.isEmpty() && filterMap.isEmpty())
            return true;

        if (!globalFilters.isEmpty()) {

            for (Filter filter : globalFilters) {
                handlerMessage = filter.intercept(handlerMessage);

                if (handlerMessage == null)
                    return false;
            }
        }

        final Set<Filter> filters = filterMap.get(actorMessage.getClass());

        if (filters == null)
            return true;

        for (Filter filter : filters) {
            handlerMessage = filter.intercept(handlerMessage);

            if (handlerMessage == null)
                return false;
        }

        return true;
    }

    private void stop(Actor actor) {

        if (isStopped)
            throw new IllegalStateException("Cannot stop an actor after shutdown() is called");

        pathToActorMap.remove(actor.getPath());
        actor.clear();
    }

    private static class EmptyActor extends Actor {
        @Override
        public void onReceive(Object message, int discriminator, ActorRef sender) {
        }
    }
}

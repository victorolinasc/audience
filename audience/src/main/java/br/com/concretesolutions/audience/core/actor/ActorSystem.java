package br.com.concretesolutions.audience.core.actor;

import android.app.Application;
import android.support.annotation.NonNull;

import br.com.concretesolutions.audience.core.MessageEvent;
import br.com.concretesolutions.audience.core.concurrent.MessageDispatcher;

/**
 * Central handler of the actorRef system. Responsible for dispatching messages and managing
 * references.
 */
public final class ActorSystem {

    private final MessageDispatcher dispatcher = new MessageDispatcher();
    private final ActorRegistry actorRegistry = new ActorRegistry();
    private final ActivityChoreography activityChoreography = new ActivityChoreography();
    private final RuleRegistry ruleRegistry = new RuleRegistry();

    private boolean stopped = false;

    /**
     * Shuts down the actorRefForTarget system.
     * <p>
     * Existing actors in the system will not be notified of this; they will just stop receiving
     * events.
     */
    public void shutdown() {
        dispatcher.shutdown();
        ruleRegistry.clear();
        actorRegistry.clear();
        stopped = true;
    }

    public void handleConfigurationChange() {
        activityChoreography.setInConfigurationChange(true);
    }

    @NonNull
    public ActorRef actorRef(@NonNull Actor target) {
        return actorRegistry.actorRefForTarget(target);
    }

    @NonNull
    public ActorRef actorRef(@NonNull Class<?> target) {
        return actorRegistry.actorRefForTarget(target);
    }

    public void stop(@NonNull ActorRef actorRef) {
        actorRegistry.clean(actorRef);
    }

    public ActorRegistry begin(Application application) {
        application.registerActivityLifecycleCallbacks(activityChoreography);
        return actorRegistry;
    }

    <T> void send(MessageEvent<T> message) {

        if (stopped)
            throw new IllegalStateException("System stopped...");

        final MessageEvent<?> messageEvent = ruleRegistry.runFilters(message);

        if (messageEvent != null)
            dispatcher.send(messageEvent);
    }

    public ActorRegistry getActorRegistry() {
        return actorRegistry;
    }

    public RuleRegistry getRuleRegistry() {
        return ruleRegistry;
    }
}

package br.com.concretesolutions.audience.core.actor;

import android.content.res.Configuration;

import br.com.concretesolutions.audience.core.concurrent.BaseHandler;
import br.com.concretesolutions.audience.core.concurrent.MessageDispatcher;

/**
 * Central handler of the actor system. Responsible for dispatching messages and managing
 * references.
 */
public final class ActorSystem {

    private final MessageDispatcher dispatcher = new MessageDispatcher();
    private final ActorRegister register = new ActorRegister();

    private boolean stopped = false;

    /**
     * Shuts down the actorForTarget system.
     * <p>
     * Existing actors in the system will not be notified of this; they will just stop receiving
     * events.
     */
    public void shutdown() {
        dispatcher.shutdown();
        stopped = true;
    }

    public void handleConfigurationChange(Configuration newConfig) {
    }

    public ActorRegister getRegister() {
        return register;
    }

    public ActorRef actor(Object target) {
        return register.actorForTarget(target);
    }

    public void send(BaseHandler queue, String target, Object actorMsg, int discriminator, ActorRef sender) {

        final ActorRef actorRef = register.actorForTarget(target);

//        final Part part = new Part(actorRef, actorMsg, sender);
//        final Message handlerMsg = queue.obtainMessage(discriminator, part);
//        final boolean proceed = executeFilter(handlerMsg, actorMsg);

//        if (proceed)
//            queue.sendMessage(handlerMsg);
    }

    private boolean handleCommonMessageSending(ActorRef target, Object message) {

        if (stopped)
            throw new IllegalStateException("Cannot send messages to an actorForTarget after shutdown() is " +
                    "called");

        return false;
    }
}

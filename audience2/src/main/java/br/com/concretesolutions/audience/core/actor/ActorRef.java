package br.com.concretesolutions.audience.core.actor;

import java.lang.ref.WeakReference;

import br.com.concretesolutions.audience.core.Director;

public final class ActorRef {

    private final WeakReference<ActorStorage> ref;

    ActorRef(ActorStorage ref) {
        this.ref = new WeakReference<>(ref);
    }

    public <T> MessageBuilder<T> tell(T message) {
        return new MessageBuilder<>(message, this);
    }

    public static class MessageBuilder<T> {

        final T message;
        final ActorRef sender;
        boolean isOnStage;

        MessageBuilder(T message, ActorRef sender) {
            this.message = message;
            this.sender = sender;
        }

        public void toActor(Object target) {
            to(Director.actor(target));
        }

        public void to(ActorRef receiver) {
//            Director.play()
//                    .send();
        }

        public MessageBuilder onStage() {
            this.isOnStage = true;
            return this;
        }
    }
}

package br.com.concretesolutions.audience.core.actor;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.MessageEvent;
import br.com.concretesolutions.audience.core.script.AssistantScript1;
import br.com.concretesolutions.audience.core.script.AssistantScript2;
import br.com.concretesolutions.audience.core.script.Script1;
import br.com.concretesolutions.audience.core.script.Script2;
import br.com.concretesolutions.audience.core.script.Script3;

public final class ActorRef {

    final String ref;

    ActorRef(String ref) {
        this.ref = ref;
    }

    public <T> MessageBuilder<T> tell(T message) {
        return new MessageBuilder<>(message, this);
    }

    public <T> ActorRef passScript(Class<T> clazz, Script1<T> script) {
        Director.getActorRegistry().storageForRef(ref).passScript(clazz, script);
        return this;
    }

    public <T> ActorRef passScript(Class<T> clazz, Script2<T> script) {
        Director.getActorRegistry().storageForRef(ref).passScript(clazz, script);
        return this;
    }

    public <T> ActorRef passScript(Class<T> clazz, Script3<T> script) {
        Director.getActorRegistry().storageForRef(ref).passScript(clazz, script);
        return this;
    }

    public ActorRef passAssistantScript(String eventRef, AssistantScript1 script) {
        Director.getActorRegistry().storageForRef(ref).passAssistantScript(eventRef, script);
        return this;
    }

    public ActorRef passAssistantScript(String eventRef, AssistantScript2 script) {
        Director.getActorRegistry().storageForRef(ref).passAssistantScript(eventRef, script);
        return this;
    }

    public static class MessageBuilder<T> {

        final ActorRef sender;
        final T message;
        ActorRef receiver;
        boolean isOnStage;

        MessageBuilder(T message, ActorRef sender) {
            this.message = message;
            this.sender = sender;
        }

        public void toActor(Class<?> target) {
            to(Director.actorRef(target));
        }

        public void to(ActorRef receiver) {
            this.receiver = receiver;
            Director.play().send(MessageEvent.create(sender, receiver, message, isOnStage));
        }

        public MessageBuilder onStage() {
            this.isOnStage = true;
            return this;
        }

        public MessageBuilder onStage(boolean isOnStage) {
            this.isOnStage = isOnStage;
            return this;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActorRef{");
        sb.append("ref='").append(ref).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

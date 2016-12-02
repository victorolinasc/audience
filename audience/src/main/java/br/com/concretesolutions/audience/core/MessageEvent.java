package br.com.concretesolutions.audience.core;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public final class MessageEvent<T> {

    public final ActorRef sender;
    public final ActorRef receiver;
    public final T message;
    public final boolean isOnStage;

    private MessageEvent(final ActorRef sender,
                         final ActorRef receiver,
                         final T message,
                         boolean isOnStage) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isOnStage = isOnStage;
    }

    public static <T> MessageEvent<T> create(final ActorRef sender,
                                             final ActorRef receiver,
                                             final T message,
                                             boolean isOnStage) {
        return new MessageEvent<>(sender, receiver, message, isOnStage);
    }

    public boolean isAssistantMessage() {
        return message instanceof String;
    }

    public Class<?> messageClass() {

        final Class<?> messageClass = message.getClass();

        if (messageClass.isAnonymousClass()
                || messageClass.getSimpleName().contains("-$Lambda$"))
            return messageClass.getInterfaces()[0];

        return messageClass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageEvent{");
        sb.append("sender=").append(sender);
        sb.append(", receiver=").append(receiver);
        sb.append(", message=").append(messageClass());
        sb.append(", isOnStage=").append(isOnStage);
        sb.append('}');
        return sb.toString();
    }
}

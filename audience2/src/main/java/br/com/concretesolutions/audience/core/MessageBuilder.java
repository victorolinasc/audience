package br.com.concretesolutions.audience.core;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public class MessageBuilder<T> {

    private final T message;
    private final ActorRef sender;
    private boolean isOnStage;
    private ActorRef receiver;

    public MessageBuilder(T message, ActorRef sender) {
        this.message = message;
        this.sender = sender;
    }

    public MessageBuilder to(ActorRef receiver) {
        this.receiver = receiver;
        return this;
    }

    public MessageBuilder onStage() {
        this.isOnStage = true;
        return this;
    }

    public void now() {

    }
}

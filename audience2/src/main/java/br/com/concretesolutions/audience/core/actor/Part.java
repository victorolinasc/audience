package br.com.concretesolutions.audience.core.actor;

final class Part {

    private ActorStorage target;
    private Object message;
    private ActorRef sender;

    public Part(ActorStorage target, Object message, ActorRef sender) {
        this.target = target;
        this.message = message;
        this.sender = sender;
    }

    public ActorStorage getTarget() {
        return target;
    }

    public ActorRef getSender() {
        return sender;
    }

    public Object getMessage() {
        return message;
    }
}

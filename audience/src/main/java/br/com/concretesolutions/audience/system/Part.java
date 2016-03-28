package br.com.concretesolutions.audience.system;

final class Part {

    private Actor target;
    private Object message;
    private ActorRef sender;

    public Part(Actor target, Object message, ActorRef sender) {
        this.target = target;
        this.message = message;
        this.sender = sender;
    }

    public Actor getTarget() {
        return target;
    }

    public ActorRef getSender() {
        return sender;
    }

    public Object getMessage() {
        return message;
    }
}

package br.com.concretesolutions.audience.core.script;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public interface Script3<T> extends Script<T> {
    void receive(T message, ActorRef sender, ActorRef self);
}

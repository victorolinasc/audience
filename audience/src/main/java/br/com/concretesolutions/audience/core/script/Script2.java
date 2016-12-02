package br.com.concretesolutions.audience.core.script;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public interface Script2<T> extends Script<T> {
    void receive(T message, ActorRef sender);
}

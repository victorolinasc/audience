package br.com.concretesolutions.audience.core.supervisor;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public interface Supervisor<T> {

    ActorRef actor(T target);

    String computeKey(T target);

    boolean hasActor(T target);

}

package br.com.concretesolutions.audience.actor;

import br.com.concretesolutions.audience.system.ActorRef;

public interface ConfigurableActor {
    void warmUp(ActorRef ref);
}

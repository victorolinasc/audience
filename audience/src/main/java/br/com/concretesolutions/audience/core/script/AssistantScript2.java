package br.com.concretesolutions.audience.core.script;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public interface AssistantScript2 extends AssistantScript {
    void receive(ActorRef sender, ActorRef self);
}

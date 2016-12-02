package br.com.concretesolutions.audience.core.script;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public interface AssistantScript extends Script<String> {
    void receive(ActorRef sender, ActorRef self);
}

package br.com.concretesolutions.audience.script.api;

import br.com.concretesolutions.audience.system.ActorRef;

public interface AssistantScript3<T> extends AssistantScript<T> {
    void onPart(Object message, int discriminator, ActorRef sender);
}

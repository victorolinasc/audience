package br.com.concretesolutions.audience.script.api;

import br.com.concretesolutions.audience.system.ActorRef;

public interface AssistantScript2<T> extends AssistantScript<T> {
    void onPart(int discriminator, ActorRef sender);
}

package br.com.concretesolutions.audience.script.api;

import br.com.concretesolutions.audience.system.ActorRef;

public interface AssistantScript1<T> extends AssistantScript<T> {
    void onPart(ActorRef sender);
}

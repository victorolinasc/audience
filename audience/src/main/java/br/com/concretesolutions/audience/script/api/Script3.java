package br.com.concretesolutions.audience.script.api;

import br.com.concretesolutions.audience.system.ActorRef;

public interface Script3<T> extends Script<T> {
    void onPart(T message, int discriminator, ActorRef sender);
}

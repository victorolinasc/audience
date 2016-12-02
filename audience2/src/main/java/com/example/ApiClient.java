package com.example;

import java.util.List;

import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;

/**
 * Singleton actor responsible for API calls
 */
public final class ApiClient implements Actor {

    public interface Api {
        List<String> callSomething();
    }

    public interface ApiRequest {
        void call(Api api, ActorRef sender, ActorRef self);
    }

    private final Api api;

    public ApiClient() {
        api = null;
    }

    public void handleMessage(ApiRequest message, ActorRef sender, ActorRef self) {
        message.call(api, sender, self);
    }
}

package br.com.concretesolutions.audience.core;

import android.app.Application;
import android.content.res.Configuration;

import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorStorage;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.core.actor.ActorSystem;

public final class Director {

    private static final ActorSystem system = new ActorSystem();

    public static ActorSystem play() {
        return system;
    }

    public static void beginShow(Application application) {
    }

    public static void onConfigurationChanged(Configuration newConfig) {
        system.handleConfigurationChange(newConfig);
    }

    public static void endShow(Application application) {
        system.shutdown();
    }

    public static void register(Actor singletonActor) {
//        system.getRegister().register(singletonActor);
    }

    public static ActorRef actor(Object target) {
        return system.actor(target);
    }
}

package br.com.concretesolutions.audience.core;

import android.app.Application;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.core.actor.ActorRegistry;
import br.com.concretesolutions.audience.core.actor.ActorSystem;
import br.com.concretesolutions.audience.core.actor.RuleRegistry;

/**
 * Main entrance point of audience API.
 */
public final class Director {

    private static final ActorSystem system = new ActorSystem();

    public static ActorSystem play() {
        return system;
    }

    /**
     * Entry point for the configuration initialization.
     *
     * @param application The {@link Application} context for registering callbacks.
     */
    public static ActorSystem beginShow(Application application) {
        return system.begin(application);
    }

    /**
     * Must be called in the {@link Application#onConfigurationChanged(Configuration)}
     */
    public static void onConfigurationChanged() {
        system.handleConfigurationChange();
    }

    public static boolean isInConfigurationChange() {
        return system.isInConfigurationChange();
    }

    public static void setInConfigurationChange(boolean inConfigurationChange) {
        system.setInConfigurationChange(inConfigurationChange);
    }

    public static void endShow() {
        system.shutdown();
    }

    @NonNull
    public static ActorRegistry getActorRegistry() {
        return system.getActorRegistry();
    }

    @NonNull
    public static RuleRegistry getRuleRegistry() {
        return system.getRuleRegistry();
    }

    @NonNull
    public static ActorRef actorRef(@NonNull Actor target) {
        return system.actorRef(target);
    }

    @NonNull
    public static ActorRef actorRef(@NonNull Class<? extends Actor> target) {
        return system.actorRef(target);
    }
}

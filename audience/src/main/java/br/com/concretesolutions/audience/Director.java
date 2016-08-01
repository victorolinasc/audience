package br.com.concretesolutions.audience;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.res.Configuration;
import android.view.View;

import br.com.concretesolutions.audience.actor.ApplicationActor;
import br.com.concretesolutions.audience.system.ActivityChoreography;
import br.com.concretesolutions.audience.system.Actor;
import br.com.concretesolutions.audience.system.ActorRef;
import br.com.concretesolutions.audience.system.ActorSystem;

public final class Director {

    public static final String ROOT_NAMESPACE = "/app/";

    private static final ActorSystem actorSystem = new ActorSystem();
    private static final ActivityChoreography activityScope = new ActivityChoreography();

    public static ActorSystem beginShow(Application application) {
        actorSystem.staffRegistry().registerRole("/app", ApplicationActor.class);
        application.registerActivityLifecycleCallbacks(activityScope);
        return actorSystem;
    }

    public static void endShow() {
        actorSystem.shutdown();
    }

    public static ActorSystem crewSystem() {
        return actorSystem;
    }

    public static ActorRef callSingletonActor(Class<? extends Actor> singletonActor) {
        return actorSystem.getOrCreateActor("/app/" + singletonActor.getCanonicalName(), singletonActor);
    }

    public static ActorRef callActor(Application application) {
        return actorSystem.getOrCreateActor("/app/");
    }

    public static ActorRef callActor(Fragment fragment) {
        final String actorPath = "/fragment/" + fragment.getClass().getCanonicalName();
        return actorSystem.getOrCreateActor(actorPath);
    }

    public static ActorRef callActor(String actorPath) {
        return actorSystem.getOrCreateActor(actorPath);
    }

    public static ActorRef callActor(View view) {
        return callActor((Activity) view.getContext());
    }

    public static ActorRef callActor(Activity activity) {
        return actorSystem.getOrCreateActor(ActivityChoreography.getActivityPath(activity));
    }

    public static void onConfigurationChanged(Configuration newConfig) {
        activityScope.setInConfigurationChange(true);
    }
}

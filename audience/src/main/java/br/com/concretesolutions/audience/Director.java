package br.com.concretesolutions.audience;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.view.View;

import br.com.concretesolutions.audience.actor.ApplicationActor;
import br.com.concretesolutions.audience.system.ActivityChoreography;
import br.com.concretesolutions.audience.system.ActorRef;
import br.com.concretesolutions.audience.system.ActorSystem;

public final class Director {

    public static final String ROOT_NAMESPACE = "/app/";

    private static ActorSystem actorSystem;

    public static ActorSystem beginShow(Application application) {
        actorSystem = new ActorSystem();
        actorSystem.staffRegistry().registerRole("/app", ApplicationActor.class);
        application.registerActivityLifecycleCallbacks(new ActivityChoreography());
        return actorSystem;
    }

    public static void endShow() {
        actorSystem.shutdown();
    }

    public static ActorSystem crewSystem() {
        return actorSystem;
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
}

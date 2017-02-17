package br.com.concretesolutions.audience.core.actor;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import br.com.concretesolutions.audience.core.Director;

final class ActivityChoreography implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        // Configuration change finished
        Director.setInConfigurationChange(false);

        // Not an actor Activity
        if (!(activity instanceof Actor)) {
            return;
        }

        Director.getActorRegistry()
                .enroll((Actor) activity, savedInstanceState);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        if (!(activity instanceof Actor))
            return;

        if (Director.isInConfigurationChange()) {
            Director.getActorRegistry()
                    .take5((Actor) activity, outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        if (!(activity instanceof Actor))
            return;

        Director.play().stop(Director.actorRef((Actor) activity));
    }
}

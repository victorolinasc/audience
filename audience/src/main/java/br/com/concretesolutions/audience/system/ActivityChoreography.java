package br.com.concretesolutions.audience.system;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.actor.ActivityActor;
import br.com.concretesolutions.audience.actor.ConfigurableActor;

public final class ActivityChoreography implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = ActivityChoreography.class.getSimpleName();

    public ActivityChoreography() {
        Director.crewSystem().staffRegistry().registerRole("/activity/*/#", ActivityActor.class);
    }

    public static String getActivityPath(Activity activity) {
        return "/activity/" + activity.getClass().getCanonicalName() + "/" + activity.hashCode();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated(" + activity.getClass().getSimpleName() + ", " + savedInstanceState + ")");

        final ActorRef ref = Director.callActor(activity);

        if (activity instanceof ConfigurableActor)
            ((ConfigurableActor) activity).warmUp(ref);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.i(TAG, "onActivityStarted(" + activity.getClass().getSimpleName() + ")");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed(" + activity.getClass().getSimpleName() + ")");
        Director.crewSystem().rebindActivityActor(getActivityPath(activity));
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.i(TAG, "onActivityPaused(" + activity.getClass().getSimpleName() + ")");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.i(TAG, "onActivityStopped(" + activity.getClass().getSimpleName() + ")");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.i(TAG, "onActivitySaveInstanceState(" + activity.getClass().getSimpleName() + ", " + outState + ")");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i(TAG, "onActivityDestroyed(" + activity.getClass().getSimpleName() + ")");
        Director.crewSystem().stop(Director.callActor(activity));
    }
}

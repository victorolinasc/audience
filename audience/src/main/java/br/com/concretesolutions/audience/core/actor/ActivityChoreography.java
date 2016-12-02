package br.com.concretesolutions.audience.core.actor;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import br.com.concretesolutions.audience.core.Director;

public final class ActivityChoreography implements Application.ActivityLifecycleCallbacks {

    private static final String OLD_ACTOR_KEY = "Audience.OLD_ACTOR_KEY";

    private boolean inConfigurationChange;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        // Configuration change finished
        inConfigurationChange = false;

        // Not an actor Activity
        if (!(activity instanceof Actor)) {
            return;
        }

        final Actor actor = (Actor) activity;

        Director.play()
                .getActorRegistry()
                .enroll(ActorRegistry.DefaultScopes.ACTIVITY, actor);

        final boolean hasOldKey = savedInstanceState != null
                && savedInstanceState.containsKey(OLD_ACTOR_KEY);

        if (hasOldKey) {

            final String newKey = ActorRegistry.DefaultScopes.ACTIVITY
                    .buildScopeKeyForActor(actor);

            Director.getActorRegistry()
                    .rebindPath(savedInstanceState.getString(OLD_ACTOR_KEY), newKey);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        if (!(activity instanceof Actor))
            return;

        if (inConfigurationChange) {
            final String currentKey = ActorRegistry.DefaultScopes.ACTIVITY
                    .buildScopeKeyForActor((Actor) activity);

            outState.putString(OLD_ACTOR_KEY, currentKey);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        if (!(activity instanceof Actor))
            return;

        Director.play().stop(Director.actorRef((Actor) activity));
    }

    void setInConfigurationChange(boolean inConfigurationChange) {
        this.inConfigurationChange = inConfigurationChange;
    }
}

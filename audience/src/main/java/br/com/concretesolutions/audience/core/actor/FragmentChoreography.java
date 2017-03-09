package br.com.concretesolutions.audience.core.actor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import br.com.concretesolutions.audience.core.Director;

public class FragmentChoreography extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);

        if (!isActor(f))
            return;

        Director.actorRegistry().enroll((Actor) f, savedInstanceState);
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        super.onFragmentSaveInstanceState(fm, f, outState);

        if (!isActor(f))
            return;

        if (Director.isInConfigurationChange()) {
            Director.actorRegistry().take5((Actor) f, outState);
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);

        if (!isActor(f))
            return;

        Director.play().stop(Director.actorRef((Actor) f));
    }

    private boolean isActor(Fragment f) {
        return f instanceof Actor;
    }
}

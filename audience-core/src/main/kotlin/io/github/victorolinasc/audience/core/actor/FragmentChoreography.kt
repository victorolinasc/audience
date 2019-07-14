package io.github.victorolinasc.audience.core.actor


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import io.github.victorolinasc.audience.core.Director

class FragmentChoreography : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentCreated(fm: FragmentManager?, f: Fragment?, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fm, f, savedInstanceState)

        if (!isActor(f))
            return

        Director.system.actorRegistry.enroll((f as Actor?)!!, savedInstanceState)
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager?, f: Fragment?, outState: Bundle?) {
        super.onFragmentSaveInstanceState(fm, f, outState)

        if (!isActor(f))
            return

        if (Director.system.isInConfigurationChange) {
            Director.system.actorRegistry.take5(f as Actor, outState!!)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment?) {
        super.onFragmentDestroyed(fm, f)

        if (!isActor(f))
            return

        Director.system.stop(Director.actorRef((f as Actor)))
    }

    private fun isActor(f: Fragment?) = f is Actor
}

package io.github.victorolinasc.audience.core.actor

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.FragmentActivity

import io.github.victorolinasc.audience.core.Director

internal class ActivityChoreography : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        // Configuration change finished
        Director.system.isInConfigurationChange = false

        if (activity is FragmentActivity) {
            activity
                    .supportFragmentManager
                    .registerFragmentLifecycleCallbacks(FragmentChoreography(), true)
        }

        // Not an actor Activity
        if (activity !is Actor) {
            return
        }

        Director.system.actorRegistry.enroll(activity as Actor, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        if (activity !is Actor)
            return

        if (Director.system.isInConfigurationChange) {
            Director.system.actorRegistry
                    .take5(activity as Actor, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {

        if (activity !is Actor)
            return

        Director.system.stop(Director.actorRef(activity as Actor))
    }
}

package io.github.victorolinasc.audience.core.actor

import android.os.Bundle
import android.support.v4.app.Fragment

import io.github.victorolinasc.audience.core.Director

abstract class SupportFragmentActor : Fragment(), Actor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Director.system.actorRegistry.enroll(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (Director.system.isInConfigurationChange) {
            Director.system.actorRegistry.take5(this, outState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Director.system.stop(Director.actorRef(this))
    }
}

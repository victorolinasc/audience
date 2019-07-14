package io.github.victorolinasc.audience.core

import android.app.Application
import android.content.res.Configuration

import io.github.victorolinasc.audience.core.actor.Actor
import io.github.victorolinasc.audience.core.actor.ActorRef
import io.github.victorolinasc.audience.core.actor.ActorRegistry
import io.github.victorolinasc.audience.core.actor.ActorSystem
import io.github.victorolinasc.audience.core.actor.RuleRegistry

/**
 * Main entrance point of audience API.
 */
object Director {

    val system = ActorSystem()

//    var isInConfigurationChange: Boolean
//        get() = system.isInConfigurationChange
//        set(inConfigurationChange) {
//            system.isInConfigurationChange = inConfigurationChange
//        }

    /**
     * Entry point for the configuration initialization.
     *
     * @param application The [Application] context for registering callbacks.
     */
    fun beginShow(application: Application) = system.begin(application)

    /**
     * Must be called in [Application.onConfigurationChanged]
     */
    fun onConfigurationChanged() {
        system.handleConfigurationChange()
    }

    fun endShow() {
        system.shutdown()
    }

    fun actorRef(target: Actor) = system.actorRef(target)
    fun actorRef(target: Class<out Actor>) = system.actorRef(target)
}

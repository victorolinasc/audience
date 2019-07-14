package io.github.victorolinasc.audience.core.actor

import android.app.Application

import io.github.victorolinasc.audience.core.Director
import io.github.victorolinasc.audience.core.MessageEvent
import io.github.victorolinasc.audience.core.concurrent.MessageDispatcher

/**
 * Central handler of the actorRef system. Responsible for dispatching messages and managing
 * references.
 */
class ActorSystem {

    val actorRegistry = ActorRegistry()
    val ruleRegistry = RuleRegistry()

    var isInConfigurationChange: Boolean = false

    private val dispatcher = MessageDispatcher()
    private val activityChoreography = ActivityChoreography()

    private var stopped = false

    /**
     * Shuts down the actorRefForTarget system.
     *
     *
     * Existing actors in the system will not be notified of this; they will just stop receiving
     * events.
     */
    fun shutdown() {
        dispatcher.shutdown()
        ruleRegistry.clear()
        actorRegistry.clear()
        stopped = true
    }

    fun handleConfigurationChange() {
        isInConfigurationChange = true
    }

    fun actorRef(target: Actor) = actorRegistry.actorRefForTarget(target)
    fun actorRef(target: Class<out Actor>) = actorRegistry.actorRefForTarget(target)

    fun stop(actorRef: ActorRef) {
        actorRegistry.clean(actorRef)
    }

    fun begin(application: Application): ActorSystem {
        application.registerActivityLifecycleCallbacks(activityChoreography)
        return this
    }

    internal fun <T> send(message: MessageEvent<T>) {

        if (stopped)
            throw IllegalStateException("System stopped...")

        val messageEvent = ruleRegistry.runFilters(message)

        if (messageEvent != null)
            dispatcher.send(messageEvent)
    }
}

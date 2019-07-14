package io.github.victorolinasc.audience.core.actor

/**
 * Defines an instance to be an actor.
 */
interface Actor {

    /**
     * Called when the instance is registered on the system.
     *
     * @param thisRef This actor's reference
     */
    fun onActorRegistered(thisRef: ActorRef)

}

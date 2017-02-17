package br.com.concretesolutions.audience.core.actor;

/**
 * Defines an instance to be an actor.
 */
public interface Actor {

    /**
     * Called when the instance is registered on the system.
     *
     * @param thisRef This actor's reference
     */
    void onActorRegistered(ActorRef thisRef);

}

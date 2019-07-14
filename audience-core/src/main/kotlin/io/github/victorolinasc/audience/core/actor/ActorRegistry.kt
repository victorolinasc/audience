package io.github.victorolinasc.audience.core.actor

import android.os.Bundle

import java.util.HashMap

import io.github.victorolinasc.audience.core.Director
import io.github.victorolinasc.audience.core.exception.TragedyException

class ActorRegistry {

    private val storage = HashMap<String, ActorStorage>(100)
    private val reboundPaths = HashMap<String, String>()

    fun storageForRef(ref: ActorRef) = storageForRef(ref.ref)

    fun storageForRef(ref: String): ActorStorage {

        if (!storage.containsKey(ref) && reboundPaths.containsKey(ref)) {
            return storage[reboundPaths[ref]]!!
        }

        if (storage.containsKey(ref)) {
            return storage[ref]!!
        }

        throw TragedyException("No actor registered")
    }

    fun enrollSingleton(actor: SingletonActor) = innerEnroll(actor, actor.javaClass.canonicalName)

    fun enroll(actor: Actor) = innerEnroll(actor, generateKey(actor))

    fun enroll(actor: Actor, savedInstanceState: Bundle?) {

        enroll(actor)

        val hasOldKey = savedInstanceState != null && savedInstanceState.containsKey(OLD_ACTOR_KEY)

        if (hasOldKey) {
            rebindPath(savedInstanceState!!.getString(OLD_ACTOR_KEY), actor)
        }
    }

    fun take5(actor: Actor, outState: Bundle) {
        outState.putString(OLD_ACTOR_KEY, actorRefForTarget(actor).ref)
    }

    fun actorRefForTarget(target: Actor): ActorRef {

        val targetKey = generateKey(target)

        if (storage.containsKey(targetKey))
            return ActorRef(targetKey)

        throw IllegalArgumentException("No actor registered with this key")
    }

    fun actorRefForTarget(target: Class<out Actor>): ActorRef {

        val targetKey = target.canonicalName

        if (storage.containsKey(targetKey))
            return ActorRef(targetKey)

        throw IllegalArgumentException("No actor registered with this key")
    }

    fun toRuleRegistry() = Director.system.ruleRegistry

    internal fun clean(actorRef: ActorRef) {
        storage.remove(actorRef.ref)
    }

    internal fun rebindPath(oldKey: String, actor: Actor) {
        reboundPaths[oldKey] = generateKey(actor)
    }

    internal fun clear() {
        storage.clear()
        reboundPaths.clear()
    }

    private fun innerEnroll(actor: Actor, key: String): ActorRegistry {
        storage[key] = ActorStorage()
        actor.onActorRegistered(ActorRef(key))
        return this
    }

    companion object {

        private const val OLD_ACTOR_KEY =
                "io.github.victorolinasc.audience.core.actor.Audience.OLD_ACTOR_KEY"

        private fun generateKey(actor: Actor) =
                actor.javaClass.canonicalName + System.identityHashCode(actor)
    }

}

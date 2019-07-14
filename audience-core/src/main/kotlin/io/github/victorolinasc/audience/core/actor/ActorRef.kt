package io.github.victorolinasc.audience.core.actor

import io.github.victorolinasc.audience.core.Director
import io.github.victorolinasc.audience.core.MessageEvent
import io.github.victorolinasc.audience.core.script.AssistantScript0
import io.github.victorolinasc.audience.core.script.AssistantScript1
import io.github.victorolinasc.audience.core.script.AssistantScript2
import io.github.victorolinasc.audience.core.script.Script1
import io.github.victorolinasc.audience.core.script.Script2
import io.github.victorolinasc.audience.core.script.Script3

data class ActorRef internal constructor(internal val ref: String) {

    fun <T> tell(message: T): MessageBuilder<T> {
        return MessageBuilder(message, this)
    }

    fun <T> passScript(clazz: Class<T>, script: Script1<T>): ActorRef {
        Director.system.actorRegistry.storageForRef(ref).passScript(clazz, script)
        return this
    }

    fun <T> passScript(clazz: Class<T>, script: Script2<T>): ActorRef {
        Director.system.actorRegistry.storageForRef(ref).passScript(clazz, script)
        return this
    }

    fun <T> passScript(clazz: Class<T>, script: Script3<T>): ActorRef {
        Director.system.actorRegistry.storageForRef(ref).passScript(clazz, script)
        return this
    }

    fun passAssistantScript(eventRef: String, script: AssistantScript0): ActorRef {
        Director.system.actorRegistry.storageForRef(ref).passAssistantScript(eventRef, script)
        return this
    }

    fun passAssistantScript(eventRef: String, script: AssistantScript1): ActorRef {
        Director.system.actorRegistry.storageForRef(ref).passAssistantScript(eventRef, script)
        return this
    }

    fun passAssistantScript(eventRef: String, script: AssistantScript2): ActorRef {
        Director.system.actorRegistry.storageForRef(ref).passAssistantScript(eventRef, script)
        return this
    }

    class MessageBuilder<T> internal constructor(internal val message: T, internal val sender: ActorRef) {

        private var isOnStage: Boolean = false

        fun toActor(target: Class<out Actor>) {
            to(Director.actorRef(target))
        }

        fun to(receiver: ActorRef) {
            Director.system.send(MessageEvent.create(sender, receiver, message, isOnStage))
        }

        fun onStage(): MessageBuilder<*> {
            this.isOnStage = true
            return this
        }

        fun onStage(isOnStage: Boolean): MessageBuilder<*> {
            this.isOnStage = isOnStage
            return this
        }
    }
}

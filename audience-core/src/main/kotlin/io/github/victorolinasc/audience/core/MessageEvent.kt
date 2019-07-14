package io.github.victorolinasc.audience.core

import io.github.victorolinasc.audience.core.actor.ActorRef

class MessageEvent<T> private constructor(val sender: ActorRef,
                                          val receiver: ActorRef,
                                          val message: T,
                                          val isOnStage: Boolean) {

    val isAssistantMessage: Boolean
        get() = message is String

    override fun toString(): String {
        val sb = StringBuilder("MessageEvent{")
        sb.append("sender=").append(sender)
        sb.append(", receiver=").append(receiver)
        sb.append(", message=").append(messageClass())
        sb.append(", isOnStage=").append(isOnStage)
        sb.append('}')
        return sb.toString()
    }

    companion object {

        fun <T> create(sender: ActorRef,
                       receiver: ActorRef,
                       message: T,
                       isOnStage: Boolean): MessageEvent<T> {
            return MessageEvent(sender, receiver, message, isOnStage)
        }
    }
}

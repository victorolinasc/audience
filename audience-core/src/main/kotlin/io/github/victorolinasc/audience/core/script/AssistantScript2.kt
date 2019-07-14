package io.github.victorolinasc.audience.core.script

import io.github.victorolinasc.audience.core.actor.ActorRef

interface AssistantScript2 : AssistantScript {
    fun receive(sender: ActorRef, self: ActorRef)
}

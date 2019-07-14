package io.github.victorolinasc.audience.core.script

import io.github.victorolinasc.audience.core.actor.ActorRef

interface AssistantScript1 : AssistantScript {
    fun receive(sender: ActorRef)
}

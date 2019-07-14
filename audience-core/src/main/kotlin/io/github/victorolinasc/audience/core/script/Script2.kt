package io.github.victorolinasc.audience.core.script

import io.github.victorolinasc.audience.core.actor.ActorRef

interface Script2<T> : Script<T> {
    fun receive(message: T, sender: ActorRef)
}

package io.github.victorolinasc.audience.core.script

interface Script1<T> : Script<T> {
    fun receive(message: T)
}

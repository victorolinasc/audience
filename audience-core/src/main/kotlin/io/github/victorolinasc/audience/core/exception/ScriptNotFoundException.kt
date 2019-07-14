package io.github.victorolinasc.audience.core.exception

class ScriptNotFoundException(message: Any) : RuntimeException("Could not find script for type: " + message.javaClass.canonicalName)

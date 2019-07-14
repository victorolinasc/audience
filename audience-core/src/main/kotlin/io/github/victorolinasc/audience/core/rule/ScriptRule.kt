package io.github.victorolinasc.audience.core.rule

import io.github.victorolinasc.audience.core.MessageEvent

interface ScriptRule {

    fun interceptScriptMessage(message: MessageEvent<*>): MessageEvent<*>

    fun shouldIntercept(messageClass: Class<*>): Boolean
}

package io.github.victorolinasc.audience.core.rule

import io.github.victorolinasc.audience.core.MessageEvent

interface AssistantRule {

    fun interceptAssistantMessage(message: MessageEvent<String>): MessageEvent<String>

    fun shouldIntercept(assistantKey: String): Boolean
}

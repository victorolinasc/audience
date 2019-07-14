package io.github.victorolinasc.audience.core.actor

import io.github.victorolinasc.audience.core.Director
import io.github.victorolinasc.audience.core.MessageEvent
import io.github.victorolinasc.audience.core.messageClass
import io.github.victorolinasc.audience.core.rule.AssistantAndScriptRule
import io.github.victorolinasc.audience.core.rule.AssistantRule
import io.github.victorolinasc.audience.core.rule.ScriptRule
import java.util.*

class RuleRegistry {

    private val scriptRules = HashSet<ScriptRule>()
    private val assistantRules = HashSet<AssistantRule>()

    fun addAssistantAndScriptRule(rule: AssistantAndScriptRule): RuleRegistry {
        addRule(rule as ScriptRule)
        addRule(rule as AssistantRule)
        return this
    }

    fun addRule(rule: ScriptRule): RuleRegistry {

        if (scriptRules.contains(rule))
            return this

        scriptRules.add(rule)
        return this
    }

    fun addRule(rule: AssistantRule): RuleRegistry {

        if (assistantRules.contains(rule))
            return this

        assistantRules.add(rule)
        return this
    }

    fun <T> runFilters(message: MessageEvent<T>): MessageEvent<*>? {
        return if (message.isAssistantMessage) runAssistantFilters(message as MessageEvent<String>) else runScriptFilters(message)
    }

    fun clear() {
        scriptRules.clear()
        assistantRules.clear()
    }

    fun toActorRegistry() = Director.system.actorRegistry

    private fun <T> runScriptFilters(message: MessageEvent<T>): MessageEvent<*>? {

        var resolvedMessage: MessageEvent<*>? = message

        for (rule in scriptRules) {
            if (rule.shouldIntercept(resolvedMessage!!.messageClass()!!)) {

                resolvedMessage = rule.interceptScriptMessage(resolvedMessage)

                if (resolvedMessage == null)
                    return null
            }
        }

        return resolvedMessage
    }

    private fun runAssistantFilters(message: MessageEvent<String>): MessageEvent<String>? {

        var resolvedMessage: MessageEvent<String>? = message

        for (rule in assistantRules) {
            if (rule.shouldIntercept(resolvedMessage!!.message)) {

                resolvedMessage = rule.interceptAssistantMessage(resolvedMessage)

                if (resolvedMessage == null)
                    return null
            }
        }

        return resolvedMessage
    }
}

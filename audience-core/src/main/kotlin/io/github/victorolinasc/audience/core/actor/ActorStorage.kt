package io.github.victorolinasc.audience.core.actor

import android.util.Log
import io.github.victorolinasc.audience.core.MessageEvent
import io.github.victorolinasc.audience.core.exception.AssistatScriptNotFoundException
import io.github.victorolinasc.audience.core.messageClass
import io.github.victorolinasc.audience.core.script.*
import java.util.*

class ActorStorage {

    private val scripts = HashMap<Class<*>, Script<*>>()
    private val assistantScripts = HashMap<String, AssistantScript>()

    fun <T> executeEvent(obj: MessageEvent<T>) {

        Log.d(TAG, "Executing event: $obj")

        @Suppress("UNCHECKED_CAST")
        if (obj.isAssistantMessage)
            handleAssistantMessage(obj as MessageEvent<String>)
        else
            handleScriptMessage(obj)
    }

    internal fun <T> passScript(clazz: Class<T>, script: Script<T>) {
        scripts[clazz] = script
    }

    internal fun passAssistantScript(eventRef: String, script: AssistantScript) {
        assistantScripts[eventRef] = script
    }

    private fun <T> handleScriptMessage(obj: MessageEvent<T>) {

        val script = scripts[obj.messageClass()] as Script<T>

        if (script != null) {

            if (script is Script1<*>) {
                (script as Script1<T>).receive(obj.message)
            } else if (script is Script2<*>) {
                (script as Script2<T>).receive(obj.message, obj.sender)
            } else if (script is Script3<*>) {
                (script as Script3<T>).receive(obj.message, obj.sender, obj.receiver)
            }
        } else {
            throw AssistatScriptNotFoundException(obj.messageClass()!!.canonicalName)
        }
    }

    private fun handleAssistantMessage(obj: MessageEvent<String>) {
        val assistantScript = assistantScripts[obj.message]

        if (assistantScript != null) {

            if (assistantScript is AssistantScript0) {
                assistantScript.receive()
            } else if (assistantScript is AssistantScript1) {
                assistantScript.receive(obj.sender)
            } else if (assistantScript is AssistantScript2) {
                assistantScript.receive(obj.sender, obj.receiver)
            }
        } else {
            throw AssistatScriptNotFoundException(obj.message)
        }
    }

    companion object {
        private val TAG = ActorStorage::class.java.name
    }
}

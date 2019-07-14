package io.github.victorolinasc.audience.core.rule

import android.util.Log

import io.github.victorolinasc.audience.core.MessageEvent

class LoggingShowRule : AssistantAndScriptRule {

    override fun shouldIntercept(assistantKey: String): Boolean {
        return true
    }

    override fun shouldIntercept(messageClass: Class<*>): Boolean {
        return true
    }

    override fun interceptAssistantMessage(message: MessageEvent<String>): MessageEvent<String> {
        Log.i(TAG, "Intercepted assistant message: $message")
        return message
    }


    override fun interceptScriptMessage(message: MessageEvent<*>): MessageEvent<*> {
        Log.i(TAG, "Intercepted script message: $message")
        return message
    }

    companion object {

        private val TAG = LoggingShowRule::class.java.simpleName
    }
}

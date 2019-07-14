package io.github.victorolinasc.audience.core.concurrent

import android.os.Handler
import android.os.Looper
import android.os.Message

import io.github.victorolinasc.audience.core.Director
import io.github.victorolinasc.audience.core.MessageEvent

abstract class BaseHandler(looper: Looper) : Handler(looper) {

    override fun handleMessage(msg: Message) {
        val event = msg.obj as MessageEvent<*>
        Director.system.actorRegistry
                .storageForRef(event.receiver)
                .executeEvent(event)
    }
}

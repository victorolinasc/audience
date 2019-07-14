package io.github.victorolinasc.audience.core.concurrent

import android.os.HandlerThread

class ActorThread : HandlerThread("ActorStorage-Thread") {

    var messageHandler: BaseHandler? = null
        private set

    @Synchronized
    override fun start() {
        super.start()
        messageHandler = BGInboxHandler(looper)
    }
}
package io.github.victorolinasc.audience.core.concurrent

import io.github.victorolinasc.audience.core.MessageEvent
import java.util.*

class MessageDispatcher {

    // Handlers --------------------
    private val schedulers = arrayOfNulls<BaseHandler>(N_THREADS)
    private val uiScheduler = UIInboxHandler()

    // Queue selection -------------
    private val mQueueSelectionRandom = Random()

    private val nextQueue: BaseHandler
        get() = schedulers[mQueueSelectionRandom.nextInt(N_THREADS)]!!

    init {
        // Initialize system
        for (i in 0 until N_THREADS) {
            val actorThread = ActorThread()
            actorThread.start()
            schedulers[i] = actorThread.messageHandler
        }
    }

    fun shutdown() {
        for (handler in schedulers)
            handler!!.looper.quit()
    }

    fun <T> send(ref: MessageEvent<T>) {
        val scheduler = if (ref.isOnStage) uiScheduler else nextQueue
        val message = scheduler.obtainMessage(0, ref)
        scheduler.sendMessage(message)
    }

    companion object {
        private const val N_THREADS = 4
    }
}

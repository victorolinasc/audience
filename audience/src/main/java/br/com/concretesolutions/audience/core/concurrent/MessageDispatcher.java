package br.com.concretesolutions.audience.core.concurrent;

import android.os.Message;

import java.util.Random;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.MessageEvent;

public final class MessageDispatcher {

    private static final int N_THREADS = 4;

    // Handlers --------------------
    private final BaseHandler[] schedulers = new BaseHandler[N_THREADS];
    private final UIInboxHandler uiScheduler = new UIInboxHandler();

    // Queue selection -------------
    private final Random mQueueSelectionRandom = new Random();

    public MessageDispatcher() {
        // Initialize system
        for (int i = 0; i < N_THREADS; i++) {
            final ActorThread actorThread = new ActorThread();
            actorThread.start();
            schedulers[i] = actorThread.getMessageHandler();
        }
    }

    public void shutdown() {
        for (BaseHandler handler : schedulers)
            handler.getLooper().quit();
    }

    public <T> void send(MessageEvent<T> ref) {
        final BaseHandler scheduler = ref.isOnStage ? uiScheduler : getNextQueue();
        final Message message = scheduler.obtainMessage(0, ref);
        scheduler.sendMessage(message);
    }

    private BaseHandler getNextQueue() {
        return schedulers[mQueueSelectionRandom.nextInt(N_THREADS)];
    }
}

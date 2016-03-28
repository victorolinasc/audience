package br.com.concretesolutions.audience.system;

import android.os.HandlerThread;

public final class ActorThread extends HandlerThread {

    private BaseHandler messageHandler;

    public ActorThread() {
        super("Actor-Thread");
    }

    @Override
    public synchronized void start() {
        super.start();
        messageHandler = new BGInboxHandler(getLooper());
    }

    public BaseHandler getMessageHandler() {
        return messageHandler;
    }
}

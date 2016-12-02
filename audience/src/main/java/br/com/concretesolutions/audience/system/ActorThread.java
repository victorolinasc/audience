package br.com.concretesolutions.audience.system;

import android.os.HandlerThread;

final class ActorThread extends HandlerThread {

    private BaseHandler messageHandler;

    ActorThread() {
        super("Actor-Thread");
    }

    @Override
    public synchronized void start() {
        super.start();
        messageHandler = new BGInboxHandler(getLooper());
    }

    BaseHandler getMessageHandler() {
        return messageHandler;
    }
}

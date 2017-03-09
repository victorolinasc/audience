package br.com.concretesolutions.audience.core.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.MessageEvent;

public abstract class BaseHandler extends Handler {

    public BaseHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        final MessageEvent event = (MessageEvent) msg.obj;
        Director.actorRegistry()
                .storageForRef(event.receiver)
                .executeEvent(event);
    }
}

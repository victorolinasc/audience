package br.com.concretesolutions.audience.core.concurrent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class BaseHandler extends Handler {

    public BaseHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
    }
}

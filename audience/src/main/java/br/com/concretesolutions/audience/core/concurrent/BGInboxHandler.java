package br.com.concretesolutions.audience.core.concurrent;

import android.os.Looper;

public final class BGInboxHandler extends BaseHandler {
    public BGInboxHandler(Looper looper) {
        super(looper);
    }
}
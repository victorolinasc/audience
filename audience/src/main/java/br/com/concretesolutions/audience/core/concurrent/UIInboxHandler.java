package br.com.concretesolutions.audience.core.concurrent;

import android.os.Looper;

public final class UIInboxHandler extends BaseHandler {
    public UIInboxHandler() {
        super(Looper.getMainLooper());
    }
}
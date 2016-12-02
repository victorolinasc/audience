package br.com.concretesolutions.audience.core.rule;

import android.util.Log;

import br.com.concretesolutions.audience.core.MessageEvent;

public final class LoggingShowRule implements AssistantAndScriptRule {

    private static final String TAG = LoggingShowRule.class.getSimpleName();

    @Override
    public boolean shouldIntercept(String assistantKey) {
        return true;
    }

    @Override
    public boolean shouldIntercept(Class<?> messageClass) {
        return true;
    }

    @Override
    public MessageEvent<String> interceptAssistantMessage(MessageEvent<String> message) {
        Log.i(TAG, "Intercepted assistant message: " + message);
        return message;
    }


    @Override
    public MessageEvent<?> interceptScriptMessage(MessageEvent<?> message) {
        Log.i(TAG, "Intercepted script message: " + message);
        return message;
    }
}

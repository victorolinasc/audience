package br.com.concretesolutions.audience.core.rule;

import br.com.concretesolutions.audience.core.MessageEvent;

public interface ScriptRule {

    MessageEvent<?> interceptScriptMessage(MessageEvent<?> message);

    boolean shouldIntercept(Class<?> messageClass);
}

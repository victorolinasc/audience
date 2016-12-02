package br.com.concretesolutions.audience.core.rule;

import br.com.concretesolutions.audience.core.MessageEvent;

public interface AssistantRule {

    MessageEvent<String> interceptAssistantMessage(MessageEvent<String> message);

    boolean shouldIntercept(String assistantKey);
}

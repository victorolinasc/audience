package br.com.concretesolutions.audience.core.actor;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.MessageEvent;
import br.com.concretesolutions.audience.core.rule.AssistantAndScriptRule;
import br.com.concretesolutions.audience.core.rule.AssistantRule;
import br.com.concretesolutions.audience.core.rule.ScriptRule;

public final class RuleRegistry {

    private final Set<ScriptRule> scriptRules = new HashSet<>();
    private final Set<AssistantRule> assistantRules = new HashSet<>();

    public RuleRegistry addAssistantAndScriptRule(@NonNull AssistantAndScriptRule rule) {
        addRule((ScriptRule) rule);
        addRule((AssistantRule) rule);
        return this;
    }

    public RuleRegistry addRule(@NonNull ScriptRule rule) {

        if (scriptRules.contains(rule))
            return this;

        scriptRules.add(rule);
        return this;
    }

    public RuleRegistry addRule(@NonNull AssistantRule rule) {

        if (assistantRules.contains(rule))
            return this;

        assistantRules.add(rule);
        return this;
    }

    public <T> MessageEvent<?> runFilters(@NonNull MessageEvent<T> message) {

        if (message.isAssistantMessage())
            //noinspection unchecked
            return runAssistantFilters((MessageEvent<String>) message);

        return runScriptFilters(message);
    }

    public void clear() {
        scriptRules.clear();
        assistantRules.clear();
    }

    public ActorRegistry toActorRegistry() {
        return Director.getActorRegistry();
    }

    private <T> MessageEvent<?> runScriptFilters(MessageEvent<T> message) {

        MessageEvent<?> resolvedMessage = message;

        for (ScriptRule rule : scriptRules) {
            if (rule.shouldIntercept(resolvedMessage.messageClass())) {

                resolvedMessage = rule.interceptScriptMessage(resolvedMessage);

                if (resolvedMessage == null)
                    return null;
            }
        }

        return resolvedMessage;
    }

    private MessageEvent<String> runAssistantFilters(MessageEvent<String> message) {

        MessageEvent<String> resolvedMessage = message;

        for (AssistantRule rule : assistantRules) {
            if (rule.shouldIntercept(resolvedMessage.message)) {

                resolvedMessage = rule.interceptAssistantMessage(resolvedMessage);

                if (resolvedMessage == null)
                    return null;
            }
        }

        return resolvedMessage;
    }
}

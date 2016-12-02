package br.com.concretesolutions.audience.core.actor;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.core.script.AssistantScript;
import br.com.concretesolutions.audience.core.script.Script;

final class ActorStorage {

    private final Map<Class<?>, Script<?>> scripts = new HashMap<>();
    private final Map<String, AssistantScript> assistantScripts = new HashMap<>();

    <T> void passScript(Class<T> clazz, Script<T> script) {
        scripts.put(clazz, script);
    }

    void passAssistantScript(String eventRef, AssistantScript script) {
        assistantScripts.put(eventRef, script);
    }

    void clear() {
        scripts.clear();
        assistantScripts.clear();
    }
}

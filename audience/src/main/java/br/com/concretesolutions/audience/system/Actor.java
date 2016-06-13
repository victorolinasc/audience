package br.com.concretesolutions.audience.system;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.script.api.AssistantScript;
import br.com.concretesolutions.audience.script.api.Script;

public abstract class Actor {

    private ActorRef self;

    private final Map<Class<?>, Script<?>> scripts = new HashMap<>();
    private final Map<String, AssistantScript<?>> referenceScripts = new HashMap<>();

    public abstract <T> void onReceive(T message, int discriminator, ActorRef sender);

    <T> void registerScript(Class<T> clazz, Script<T> script) {
        scripts.put(clazz, script);
    }

    void registerScript(String eventRef, AssistantScript script) {
        referenceScripts.put(eventRef, script);
    }

    void setSelf(ActorRef self) {
        this.self = self;
    }

    void clear() {
        scripts.clear();
        referenceScripts.clear();
    }

    Map<Class<?>, Script<?>> getScripts() {
        return scripts;
    }

    Map<String, AssistantScript<?>> getReferenceScripts() {
        return referenceScripts;
    }

    public ActorRef self() {
        return self;
    }

    public String getPath() {
        return self.getPath();
    }
}

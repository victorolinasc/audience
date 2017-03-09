package br.com.concretesolutions.audience.core.actor;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.core.MessageEvent;
import br.com.concretesolutions.audience.core.exception.AssistatScriptNotFoundException;
import br.com.concretesolutions.audience.core.script.AssistantScript;
import br.com.concretesolutions.audience.core.script.AssistantScript0;
import br.com.concretesolutions.audience.core.script.AssistantScript1;
import br.com.concretesolutions.audience.core.script.AssistantScript2;
import br.com.concretesolutions.audience.core.script.Script;
import br.com.concretesolutions.audience.core.script.Script1;
import br.com.concretesolutions.audience.core.script.Script2;
import br.com.concretesolutions.audience.core.script.Script3;

public final class ActorStorage {

    private static final String TAG = ActorStorage.class.getName();

    private final Map<Class<?>, Script<?>> scripts = new HashMap<>();
    private final Map<String, AssistantScript> assistantScripts = new HashMap<>();

    <T> void passScript(Class<T> clazz, Script<T> script) {
        scripts.put(clazz, script);
    }

    void passAssistantScript(String eventRef, AssistantScript script) {
        assistantScripts.put(eventRef, script);
    }

    public <T> void executeEvent(MessageEvent<T> obj) {

        Log.d(TAG, "Executing event: " + obj);

        if (obj.isAssistantMessage())
            // noinspection unchecked
            handleAssistantMessage((MessageEvent<String>) obj);

        else
            handleScriptMessage(obj);
    }

    <T> void handleScriptMessage(MessageEvent<T> obj) {
        // noinspection unchecked
        final Script<T> script = (Script<T>) scripts.get(obj.messageClass());

        if (script != null) {

            if (script instanceof Script1) {
                ((Script1<T>) script).receive(obj.message);
            } else if (script instanceof Script2) {
                ((Script2<T>) script).receive(obj.message, obj.sender);
            } else if (script instanceof Script3) {
                ((Script3<T>) script).receive(obj.message, obj.sender, obj.receiver);
            }
        } else {
            throw new AssistatScriptNotFoundException(obj.messageClass().getCanonicalName());
        }
    }

    void handleAssistantMessage(MessageEvent<String> obj) {
        final AssistantScript assistantScript = assistantScripts.get(obj.message);

        if (assistantScript != null) {

            if (assistantScript instanceof AssistantScript0) {
                ((AssistantScript0) assistantScript).receive();
            } else if (assistantScript instanceof AssistantScript1) {
                ((AssistantScript1) assistantScript).receive(obj.sender);
            } else if (assistantScript instanceof AssistantScript2) {
                ((AssistantScript2) assistantScript).receive(obj.sender, obj.receiver);
            }
        } else {
            throw new AssistatScriptNotFoundException(obj.message);
        }
    }
}

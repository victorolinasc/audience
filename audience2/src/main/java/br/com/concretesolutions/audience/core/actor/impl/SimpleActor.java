package br.com.concretesolutions.audience.core.actor.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.core.actor.ActorStorage;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.core.exception.TragedyException;
import br.com.concretesolutions.audience.core.script.Script;

public class SimpleActor implements ActorStorage<Object> {

    private final Map<Class<?>, Script<?>> scripts = new HashMap<>();
    private final Map<String, Script<String>> assistantScripts = new HashMap<>();

    @Override
    public void onMessage(Object message, ActorRef sender) {

        final Script script = message instanceof String
                ? assistantScripts.get(message)
                : scripts.get(message.getClass());

        if (script == null)
            throw new TragedyException("No script for that message.");

        // noinspection unchecked
        script.onMessage(message, sender);
    }
}

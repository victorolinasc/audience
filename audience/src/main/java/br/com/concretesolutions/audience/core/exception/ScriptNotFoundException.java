package br.com.concretesolutions.audience.core.exception;

public class ScriptNotFoundException extends RuntimeException {

    public ScriptNotFoundException(Object message) {
        super("Could not find script for type: " + message.getClass().getCanonicalName());
    }
}

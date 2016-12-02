package br.com.concretesolutions.audience.core.exception;

public class AssistatScriptNotFoundException extends RuntimeException {

    public AssistatScriptNotFoundException(String message) {
        super("There was no assistant script for the message: " + message);
    }
}

package br.com.concretesolutions.audience.system.message;

public final class Message {

    private Object content;

    public <T> T getContent() {
        // noinspection unchecked
        return (T) content;
    }
}

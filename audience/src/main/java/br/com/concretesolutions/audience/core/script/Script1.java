package br.com.concretesolutions.audience.core.script;

public interface Script1<T> extends Script<T> {
    void receive(T message);
}

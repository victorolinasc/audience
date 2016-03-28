package br.com.concretesolutions.audience.script.api;

public interface Script1<T> extends Script<T> {
    void onPart(T message);
}

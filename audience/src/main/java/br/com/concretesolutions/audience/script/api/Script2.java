package br.com.concretesolutions.audience.script.api;

public interface Script2<T> extends Script<T> {
    void onPart(T message, int discriminator);
}

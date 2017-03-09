package br.com.concretesolutions.audience.core;

final class Utils {

    private Utils() {
        throw new IllegalAccessError("Cannot instantiate type");
    }

    static Class<?> messageClass(Object message) {

        final Class<?> messageClass = message.getClass();

        if (messageClass.isAnonymousClass()
                || messageClass.getSimpleName().contains("-$Lambda$")  // Jack
                || messageClass.getSimpleName().contains("$$Lambda$")) // Retrolambda
            return messageClass.getInterfaces()[0];

        return messageClass;
    }
}
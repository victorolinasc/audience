package br.com.concretesolutions.audience.core;

public final class ClassUtil {

    private ClassUtil() {
        throw new IllegalStateException("Can't be instantiated");
    }

    public static Class<?> messageClass(Object message) {

        final Class<?> messageClass = message.getClass();

        if (messageClass.isAnonymousClass()
                || messageClass.getSimpleName().contains("-$Lambda$") // Jack
                || messageClass.getSimpleName().contains("$$Lambda$") // Retrolambda
                )
            return messageClass.getInterfaces()[0];

        return messageClass;
    }
}

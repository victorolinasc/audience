package br.com.concretesolutions.audience;

import br.com.concretesolutions.audience.tragedy.TragedyException;

public final class ShowRules {

    public static void checkNotNullOrEmpty(String value) {
        checkNotNullOrEmpty(value, "Got null value");
    }

    public static void checkNotNullOrEmpty(String value, String message) {
        if (value == null || "".equals(value))
            throw new TragedyException(message);
    }
}

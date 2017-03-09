package br.com.concretesolutions.audience.sample;

import static org.junit.Assert.fail;

public final class TestUtils {

    private static final long DEFAULT_WAIT = 400L;

    public static void doWait() {
        doWait(DEFAULT_WAIT);
    }

    public static void doWait(long wait) {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail("Could not sleep.");
        }
    }
}

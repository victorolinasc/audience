package com.example.audience2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest3 {

    interface Blah {}

    static class Blergh implements Blah {}

    @Test
    public void addition_isCorrect() throws Exception {
        assertTrue(Blah.class.isAssignableFrom(Blergh.class));
    }
}
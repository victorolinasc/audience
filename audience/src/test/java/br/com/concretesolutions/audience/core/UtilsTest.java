package br.com.concretesolutions.audience.core;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class UtilsTest {

    interface SAMType {
        void doSmt();
    }

    @Test
    public void messageClass_canMatchOnAnonymousClass() {
        SAMType instance = new SAMType() {
            @Override
            public void doSmt() {
            }
        };

        assertThat(Utils.messageClass(instance),
                CoreMatchers.<Class<?>>equalTo(SAMType.class));
    }
}

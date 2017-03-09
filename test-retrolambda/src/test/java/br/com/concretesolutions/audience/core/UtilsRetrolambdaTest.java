package br.com.concretesolutions.audience.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UtilsRetrolambdaTest {

    interface SAMType {
        void doSmt();
    }

    @Test
    public void messageClass_canDetectTypeOfRetrolambdaInstance() {

        SAMType instance = () -> {
        };

        assertThat(Utils.messageClass(instance), equalTo(SAMType.class));
    }
}
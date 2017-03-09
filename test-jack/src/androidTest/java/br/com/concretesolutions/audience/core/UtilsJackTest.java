package br.com.concretesolutions.audience.core;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class UtilsJackTest {

    /**
     * Single Abstract Method interface type
     */
    interface SAMType {
        void doSmt();
    }

    @Test
    public void messageClass_canDetectTypeOfJackInstance() {

        // This is a generated class in Jack running before Nougat
        SAMType instance = () -> {
        };

        assertThat(Utils.messageClass(instance), equalTo(SAMType.class));
    }
}
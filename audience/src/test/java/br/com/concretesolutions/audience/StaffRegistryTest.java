package br.com.concretesolutions.audience;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.io.IOException;

import br.com.concretesolutions.audience.actor.ActivityActor;
import br.com.concretesolutions.audience.system.ActivityChoreography;
import br.com.concretesolutions.audience.system.Actor;
import br.com.concretesolutions.audience.system.ActorRef;
import br.com.olinasc.audience.BuildConfig;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class StaffRegistryTest {

    @Before
    public void setUp() throws IOException {
        ShadowLog.stream = System.out;
        Director.beginShow(RuntimeEnvironment.application);
    }

    @Test
    public void canRegistry() {

        final Activity activity1 = new Activity();
        final ActorRef actorRef = Director.callActor(activity1);
        assertThat(actorRef.getPath(), startsWith("/activity/android.app.Activity/"));

        final ActorRef actorRef2 = Director.callActor(new Activity());
        assertThat(actorRef2.getPath(), startsWith("/activity/android.app.Activity/"));

        assertThat(actorRef.getPath(), not(actorRef2.getPath()));

        final ActorRef actorRef1 = Director.callActor(ActivityChoreography.getActivityPath(activity1));
        assertThat(actorRef.getPath(), is(actorRef1.getPath()));
    }

    @Test
    public void testPathRegistry() {

        final Class<? extends Actor> lookup = Director
                .crewSystem()
                .staffRegistry()
                .lookup("/activity/android.app.Activity/123456");

        assertThat(lookup.getCanonicalName(), is(ActivityActor.class.getCanonicalName()));
    }
}
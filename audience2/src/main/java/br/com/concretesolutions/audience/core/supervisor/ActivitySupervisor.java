package br.com.concretesolutions.audience.core.supervisor;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public final class ActivitySupervisor implements Supervisor<Activity> {

    private final Map<String, ActorRef> activityActorMap = new HashMap<>(100);

    @Override
    public ActorRef actor(Activity target) {
        return null;
    }

    @Override
    public String computeKey(Activity target) {
        return target.getClass().getSimpleName() + "/" + System.identityHashCode(target);
    }

    @Override
    public boolean hasActor(Activity target) {
        return false;
    }
}

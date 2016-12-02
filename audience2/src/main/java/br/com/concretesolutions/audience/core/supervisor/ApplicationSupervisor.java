package br.com.concretesolutions.audience.core.supervisor;

import android.app.Application;

import br.com.concretesolutions.audience.core.actor.ActorRef;

public final class ApplicationSupervisor implements Supervisor<Application> {
    @Override
    public ActorRef actor(Application target) {
        return null;
    }

    @Override
    public String computeKey(Application target) {
        return null;
    }

    @Override
    public boolean hasActor(Application target) {
        return false;
    }
}

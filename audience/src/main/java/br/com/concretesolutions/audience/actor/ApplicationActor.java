package br.com.concretesolutions.audience.actor;

import br.com.concretesolutions.audience.system.Actor;
import br.com.concretesolutions.audience.system.ActorRef;

public class ApplicationActor extends Actor {

    @Override
    public void onReceive(Object message, int discriminator, ActorRef sender) {}
}

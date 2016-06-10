package br.com.concretesolutions.audience.actor;

import android.os.Looper;

import br.com.concretesolutions.audience.system.ActorRef;
import br.com.concretesolutions.audience.system.message.PoisonPill;

public final class ActorUtils {

    private ActorUtils() {}

    public static void assertIsNotOnStage(ActorRef sender) {

        if (!Looper.getMainLooper().equals(Looper.myLooper()))
            return;

        final IllegalStateException exception = new IllegalStateException("Must not be on Stage (UiTHread)");
        sender.tell(new PoisonPill("Can't be on UiThread", exception));
        throw exception;
    }

    public static void assertMessageIsOfType(Object message, Class<?> type, ActorRef sender) {

        if (message.getClass() == type)
            return;

        final IllegalArgumentException exception =
                new IllegalArgumentException(
                        String.format("Was expecting to get type %s but got %s instead.",
                                type.getName(), message.getClass().getName()));

        sender.tell(new PoisonPill("Can't deal with that type", exception));
        throw exception;
    }
}

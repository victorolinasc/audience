package br.com.concretesolutions.audience.core.actor;

import android.app.Activity;
import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.core.exception.TragedyException;

final class ActorRegister {

    private final Map<String, ActorStorage> actorRef = new HashMap<>(100);

    // app -> activity -> fragment -> view
    //           |     -> view

    ActorRef actorForTarget(Object target) {

        final String key;

        if (target instanceof Application)
            key = "application";

        else if (target instanceof Activity)
            key = computeKey((Activity) target);

        else if (target instanceof String)
            key = (String) target;

        else if (target instanceof Class<?>)
            key = ((Class<?>) target).getCanonicalName();
        else
            key = "";

        if (!actorRef.containsKey(key))
            tryCreatingActor(key, target);

        final ActorStorage actor = actorRef.get(key);

        if (actor == null)
            throw new TragedyException("Could not create actor");

        return new ActorRef(actor);
    }

    // TODO
    private void tryCreatingActor(String key, Object target) {
        // this.actorRef.put(key, new SimpleActor());
    }

    private String computeKey(Activity activity) {
        return activity.getClass().getCanonicalName() + "/" + System.identityHashCode(activity);
    }
}

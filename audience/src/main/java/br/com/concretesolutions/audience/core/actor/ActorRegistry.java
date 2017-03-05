package br.com.concretesolutions.audience.core.actor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.exception.TragedyException;

public final class ActorRegistry {

    private static final String OLD_ACTOR_KEY =
            "br.com.concretesolutions.audience.core.actor.Audience.OLD_ACTOR_KEY";

    private final Map<String, ActorStorage> storage = new HashMap<>(100);
    private final Map<String, String> reboundPaths = new HashMap<>();

    @NonNull
    public ActorStorage storageForRef(@NonNull ActorRef ref) {
        return storageForRef(ref.ref);
    }

    @NonNull
    public ActorStorage storageForRef(@NonNull String ref) {

        if (!storage.containsKey(ref) && reboundPaths.containsKey(ref)) {
            return storage.get(reboundPaths.get(ref));
        }

        if (storage.containsKey(ref)) {
            return storage.get(ref);
        }

        throw new TragedyException("No actor registered");
    }

    public ActorRegistry enrollSingleton(@NonNull SingletonActor actor) {
        return innerEnroll(actor, actor.getClass().getCanonicalName());
    }

    public ActorRegistry enroll(@NonNull Actor actor) {
        return innerEnroll(actor, generateKey(actor));
    }

    private ActorRegistry innerEnroll(@NonNull final Actor actor, @NonNull final String key) {
        storage.put(key, new ActorStorage());
        actor.onActorRegistered(new ActorRef(key));
        return this;
    }

    public void enroll(@NonNull Actor actor, @Nullable Bundle savedInstanceState) {

        enroll(actor);

        final boolean hasOldKey = savedInstanceState != null
                && savedInstanceState.containsKey(OLD_ACTOR_KEY);

        if (hasOldKey) {
            rebindPath(savedInstanceState.getString(OLD_ACTOR_KEY), actor);
        }
    }

    public void take5(Actor actor, Bundle outState) {
        outState.putString(OLD_ACTOR_KEY, actorRefForTarget(actor).ref);
    }

    @NonNull
    public ActorRef actorRefForTarget(@NonNull Actor target) {

        final String targetKey = generateKey(target);

        if (storage.containsKey(targetKey))
            return new ActorRef(targetKey);

        throw new IllegalArgumentException("No actor registered with this key");
    }

    @NonNull
    public ActorRef actorRefForTarget(@NonNull Class<? extends Actor> target) {

        final String targetKey = target.getCanonicalName();

        if (storage.containsKey(targetKey))
            return new ActorRef(targetKey);

        throw new IllegalArgumentException("No actor registered with this key");
    }

    public RuleRegistry toRuleRegistry() {
        return Director.getRuleRegistry();
    }

    void clean(ActorRef actorRef) {
        storage.remove(actorRef.ref);
    }

    void rebindPath(String oldKey, Actor actor) {
        reboundPaths.put(oldKey, generateKey(actor));
    }

    void clear() {
        storage.clear();
        reboundPaths.clear();
    }

    private static String generateKey(Actor actor) {
        return actor.getClass().getCanonicalName()
                + System.identityHashCode(actor);
    }

}

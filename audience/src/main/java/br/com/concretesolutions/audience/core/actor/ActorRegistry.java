package br.com.concretesolutions.audience.core.actor;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.concretesolutions.audience.core.exception.TragedyException;

public final class ActorRegistry {

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

    public void enroll(@NonNull Object reference, @NonNull Actor actor) {
        enroll(DefaultScopes.scopeForClass(reference), actor);
    }

    public void enroll(@NonNull DefaultScopes scope, @NonNull Actor actor) {
        final String key = scope.buildScopeKeyForActor(actor);
        storage.put(key, new ActorStorage());
        actor.onActorRegistered(new ActorRef(key));
    }

    @NonNull
    public ActorRef actorRefForTarget(@NonNull Actor target) {

        final String key;

        if (target instanceof Activity) {
            key = generateKey(target, DefaultScopes.ACTIVITY.scopeName);
        } else if (target instanceof View) {
            key = generateKey(target, DefaultScopes.VIEW.scopeName);
        } else {
            key = generateKey(target, DefaultScopes.APPLICATION.scopeName);
        }

        if (storage.containsKey(key))
            return new ActorRef(key);

        throw new IllegalArgumentException("No actor registered with this key");
    }

    public void clean(ActorRef actorRef) {
        storage.remove(actorRef.ref);
    }

    public ActorRef actorRefForTarget(Class<?> target) {

        final String key = DefaultScopes.APPLICATION.scopeName + target.getCanonicalName();

        if (!storage.containsKey(key))
            throw new IllegalArgumentException("No actor registered for reference");

        return new ActorRef(key);
    }

    public void rebindPath(String oldKey, String newKey) {
        reboundPaths.put(oldKey, newKey);
    }

    public enum DefaultScopes {

        APPLICATION("_application/", Collections.singletonList(Application.class)) {
            @Override
            String buildScopeKeyForActor(Actor actor) {
                return this.scopeName + actor.getClass().getCanonicalName();
            }
        },
        ACTIVITY("_activity/", Collections.singletonList(Activity.class)),
        VIEW("_view/", Collections.singletonList(View.class));

        final String scopeName;
        private final List<Class<?>> refClass;

        DefaultScopes(String scopeName, List<Class<?>> clazz) {
            this.scopeName = scopeName;
            this.refClass = clazz;
        }

        String buildScopeKeyForActor(Actor actor) {
            return generateKey(actor, scopeName);
        }

        static DefaultScopes scopeForClass(Object target) {
            for (DefaultScopes scope : values()) {
                for (Class<?> refClas : scope.refClass) {
                    if (refClas.isAssignableFrom(target.getClass())) {
                        return scope;
                    }
                }
            }

            throw new IllegalArgumentException("Target scope is non existent");
        }
    }

    void clear() {
        storage.clear();
        reboundPaths.clear();
    }

    private static String generateKey(Actor actor, String scope) {
        return scope + actor.getClass().getCanonicalName() + System.identityHashCode(actor);
    }
}

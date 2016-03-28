package br.com.concretesolutions.audience.system;

import android.content.UriMatcher;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.ShowRules;
import br.com.concretesolutions.audience.tragedy.TragedyException;

/**
 * ActorRegistry provides a Uri -> Actor class lookup mechanism.
 */
public final class ActorRegistry {

    public static final String ACTOR_SCHEME = "actor";
    public static final String LOCAL_AUTHORITY = "local";

    // Important: Use non-negative integers since UriMatcher.NO_MATCH == -1.
    private static int nextMatch = 0;
    private static SparseArray<Class<? extends Actor>> matchToClass = new SparseArray<>();
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private ActorRegistry() {
    }

    public static void register(String uriString, Class<? extends Actor> cls) {

        ShowRules.checkNotNullOrEmpty(uriString, "Uri may not be null");

        if (!uriString.startsWith(Director.ROOT_NAMESPACE))
            throw new TragedyException("Uri does not begin with /app.");

        final Uri uri = buildActorUri(buildActorUri(uriString));
        throwIfRegistered(uri);

        final int match = getNextMatch();
        uriMatcher.addURI(uri.getAuthority(), uri.getPath(), match);
        matchToClass.put(match, cls);
    }

    /**
     * Returns the Actor class corresponding to the given Uri if one has been registered. If the
     * Uri has not been registered, null is returned.
     *
     * @param uriString the requested uri
     */
    @Nullable
    public static Class<? extends Actor> lookup(final String uriString) {
        int match = uriMatcher.match(buildActorUri(uriString));
        return matchToClass.get(match, null);
    }

    /**
     * Clears the ActorRegistry.
     */
    public static void clear() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        matchToClass.clear();
        nextMatch = 0;
    }

    /**
     * Throws if the given uri is already registered in the ActorRegistry.
     *
     * @param uri uri to check
     */
    private static void throwIfRegistered(Uri uri) {
        if (uriMatcher.match(uri) != UriMatcher.NO_MATCH) {
            String msg = "The uri " + uri + " has already been registered";
            throw new IllegalStateException(msg);
        }
    }

    @NonNull
    private static Uri buildActorUri(String uriString) {
        return buildActorUri(Uri.parse(uriString));
    }

    @NonNull
    private static Uri buildActorUri(Uri uri) {

        return new Uri.Builder()
                .authority(getAuthority(uri))
                .scheme(ACTOR_SCHEME)
                .path(uri.getPath())
                .build();
    }

    private static String getAuthority(Uri uri) {
        return TextUtils.isEmpty(uri.getAuthority())
                ? LOCAL_AUTHORITY
                : uri.getAuthority();
    }

    private static int getNextMatch() {
        return nextMatch++;
    }
}

package br.com.concretesolutions.audience.system;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import br.com.concretesolutions.audience.tragedy.TragedyException;

public final class StaffRegistry {

    private static final String ROOT_NS_MESSAGE = "All actors must have a known root namespace";
    private static final String[] ROOT_NAMESPACES = {"/app", "/activity", "/fragment", "/service", "/receiver"};
    private final Map<Pattern, Class<? extends Actor>> registry = new HashMap<>();

    public StaffRegistry() {
    }

    public Class<? extends Actor> lookup(String fullPath) {

        if (fullPath.charAt(fullPath.length() - 1) != '/')
            fullPath += '/';

        for (Pattern pattern : registry.keySet()) {
            if (pattern.matcher(fullPath).matches())
                return registry.get(pattern);
        }

        throw new TragedyException("There are no actors registered for this path. Path: " + fullPath);
    }

    public <T extends Actor> void registerRole(String fullPath, Class<T> actorClass) {

        checkRootNamespace(fullPath);

        final String[] paths = fullPath.split("/");

        final StringBuilder builder = new StringBuilder();

        for (String path : paths) {

            switch (path) {
                case "*":
                    builder.append("[^/]+");
                    break;
                case "#":
                    builder.append("(\\+|-)?([0-9]+)");
                    break;
                default:
                    builder.append(path);
                    break;
            }

            builder.append("/");
        }

        registry.put(Pattern.compile(builder.toString()), actorClass);
    }

    private void checkRootNamespace(String path) {

        for (int i = 0; i < ROOT_NAMESPACES.length; i++) {

            if (path.startsWith(ROOT_NAMESPACES[i]))
                return;
        }

        throw new TragedyException(ROOT_NS_MESSAGE);
    }
}

package br.com.concretesolutions.audience.sample.test;

import br.com.concretesolutions.requestmatcher.RequestMatcherRule;
import br.com.concretesolutions.requestmatcher.model.HttpMethod;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.SocketPolicy;

import static org.junit.Assert.fail;

public final class TestRequests {

    private final RequestMatcherRule rule;

    public TestRequests(RequestMatcherRule rule) {
        this.rule = rule;
    }

    public TestRequests exceptionWithStatus(int status) {
        check(status >= 400 && status < 600);
        rule.addResponse(new MockResponse().setResponseCode(status));
        return this;
    }

    public TestRequests networkException() {
        rule.addResponse(new MockResponse()
                .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START));
        return this;
    }

    public TestRequests searchRepositories(String page) {
        rule.addFixture(200, "get_repositories/page" + page + ".json")
                .ifRequestMatches()
                .methodIs(HttpMethod.GET)
                .pathIs("/search/repositories")
                .queriesContain("page", page)
                .queriesContain("q", "language:java")
                .queriesContain("sort", "stars");
        return this;
    }

    private void check(boolean condition) {
        if (!condition)
            fail("Condition in test not met");
    }
}

package br.com.concretesolutions.audience.sample.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.TestUtils;
import br.com.concretesolutions.audience.sample.data.api.ApiProvider;
import br.com.concretesolutions.audience.sample.ui.activity.RepositoriesActivity;
import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule;
import br.com.concretesolutions.requestmatcher.RequestMatcherRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class RepositoriesActivityTest {

    @Rule
    public final RequestMatcherRule serverRule = new InstrumentedTestRequestMatcherRule();

    @Rule
    public final ActivityTestRule<RepositoriesActivity> activityRule =
            new ActivityTestRule<>(RepositoriesActivity.class, true, false);

    private final TestRequests mockRequests = new TestRequests(serverRule);

    @Before
    public void setUp() {
        ApiProvider.init(serverRule.url("/").toString());
    }

    @Test
    public void emptyStateWithNetworkException_shouldShowTryAgain() {

        // Arrange
        mockRequests.networkException();

        // Act
        activityRule.launchActivity(new Intent());
        TestUtils.doWait();

        // Assert
        onView(withId(R.id.empty_state_error_message))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.error_no_network)));
    }

    @Test
    public void emptyStateWithClientException_shouldShowTryAgain() {

        // Arrange
        mockRequests.exceptionWithStatus(400);

        // Act
        activityRule.launchActivity(new Intent());
        TestUtils.doWait();

        // Assert
        onView(withId(R.id.empty_state_error_message))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.error_generic_api_error)));
    }

    @Test
    public void emptyStateWithServerException_shouldShowTryAgain() {

        // Arrange
        mockRequests.exceptionWithStatus(500);

        // Act
        activityRule.launchActivity(new Intent());
        TestUtils.doWait();

        // Assert
        onView(withId(R.id.empty_state_error_message))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.error_generic_api_error)));
    }

    @Test
    public void repositoriesList_shouldLoadFirstPage() {

        // Arrange
        mockRequests.searchRepositories("1");

        // Act
        activityRule.launchActivity(new Intent());
        TestUtils.doWait();

        // Assert
        onView(withId(R.id.repositories_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void repositoriesList_shouldLoadMoreWhenScrollingToTheBottom() {

        // Arrange
        mockRequests
                .searchRepositories("1")
                .searchRepositories("2");

        // Act
        activityRule.launchActivity(new Intent());
        TestUtils.doWait();

        // Assert
        onView(withId(R.id.repositories_list))
                .check(matches(isDisplayed()))
        .perform(scrollTo());

    }
}

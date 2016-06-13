package br.com.concretesolutions.audience.demo.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.actor.ActorUtils;
import br.com.concretesolutions.audience.actor.ConfigurableActor;
import br.com.concretesolutions.audience.demo.R;
import br.com.concretesolutions.audience.demo.api.Api;
import br.com.concretesolutions.audience.demo.api.ApiClient;
import br.com.concretesolutions.audience.demo.api.Sort;
import br.com.concretesolutions.audience.demo.api.model.PageResultVO;
import br.com.concretesolutions.audience.demo.api.model.RepositoryVO;
import br.com.concretesolutions.audience.script.api.Script1;
import br.com.concretesolutions.audience.system.ActorRef;
import timber.log.Timber;

public final class RepositoriesActivity extends AppCompatActivity implements ConfigurableActor {

    @Override
    public void warmUp(ActorRef ref) {
        ref.passScript(PageResultVO.class, this::onPageReceived);
    }

    private ActorRef thisRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Audience_Theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        thisRef = Director.callActor(this);
        initToolbar();
    }

    public void clickOnLoadPage(View view) {
        Director.callActor("/app/apiclient").tell(
                (ApiClient.ApiCall) (api, sender1) -> {
                    SystemClock.sleep(1000);
                    sender1.tellOnStage(api.getRepositories("language:Java", Sort.stars, 1).execute().body());
                }, thisRef);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // no up task from here =(
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onPageReceived(PageResultVO<RepositoryVO> page) {
        Timber.d("Received repositories");
        Toast.makeText(RepositoriesActivity.this, "Got Page!", Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

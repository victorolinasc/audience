package br.com.concretesolutions.audience.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.function.Function;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.actor.ConfigurableActor;
import br.com.concretesolutions.audience.demo.R;
import br.com.concretesolutions.audience.demo.api.ApiClient;
import br.com.concretesolutions.audience.demo.api.Sort;
import br.com.concretesolutions.audience.demo.api.model.PageResultVO;
import br.com.concretesolutions.audience.demo.api.model.RepositoryVO;
import br.com.concretesolutions.audience.system.ActorRef;

public final class RepositoriesActivity extends AppCompatActivity implements ConfigurableActor {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Audience_Theme); // re-set theme
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        initToolbar();

        // noinspection ConstantConditions
        findViewById(R.id.btn_call_api).setOnClickListener(v -> callApi());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // no up task from here =(
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void warmUp(ActorRef ref) {
        ref.passScript(PageResultVO.class, this::updatePage);
//                .passAssistantScript("showLoading", sender -> showLoading())
//                .passAssistantScript("hideLoading", sender -> hideLoading());
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void callApi() {

        final ApiClient.ApiCall apiCall = (api, ref) -> {

            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ref.tellOnStage(api
                    .getRepositories("language:Java", Sort.STARS, 1)
                    .execute()
                    .body());
        };

        Director.callSingletonActor(ApiClient.class)
                .tell(apiCall, Director.callActor(RepositoriesActivity.this));
    }

    private void updatePage(PageResultVO<RepositoryVO> message) {
        Toast.makeText(this, "Got Page!", Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {}

    private void hideLoading() {}
}

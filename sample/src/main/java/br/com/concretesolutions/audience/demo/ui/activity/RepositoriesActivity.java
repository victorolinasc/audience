package br.com.concretesolutions.audience.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.demo.R;
import br.com.concretesolutions.audience.demo.api.ApiClient;
import br.com.concretesolutions.audience.demo.api.Sort;
import br.com.concretesolutions.audience.demo.api.model.PageResultVO;
import br.com.concretesolutions.audience.demo.api.model.RepositoryVO;

public final class RepositoriesActivity
        extends AppCompatActivity
        implements Actor {

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
    public void onActorRegistered(ActorRef ref) {
        ref.passScript(PageResultVO.class, this::updatePage);
    }

    private void updatePage(PageResultVO<RepositoryVO> message) {
        hideLoading();
        Toast.makeText(this,
                "Got Page! Length: " + message.getItems().size(),
                Toast.LENGTH_SHORT).show();
    }

    private void initToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showLoading() {}

    private void hideLoading() {}

    private void callApi() {

        showLoading();

        final ApiClient.ApiCall call = (api, self, sender) -> self.tell(api
                .getRepositories("language:Java", Sort.STARS, 1)
                .execute()
                .body())
                .onStage()
                .to(sender);

        Director.actorRef(this)
                .tell(call)
                .toActor(ApiClient.class);
    }
}

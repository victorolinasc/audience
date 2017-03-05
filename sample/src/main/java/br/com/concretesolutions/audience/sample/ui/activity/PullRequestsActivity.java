package br.com.concretesolutions.audience.sample.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.data.api.model.RepositoryVO;
import br.com.concretesolutions.audience.sample.ui.adapter.PullRequestsPagerAdapter;
import butterknife.BindView;

public class PullRequestsActivity extends BaseActivity implements Actor {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrequests);

        final RepositoryVO repository = getIntent().getExtras().getParcelable("repository");

        adjustToolbar(repository);
        setupViewPager(repository);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onActorRegistered(ActorRef thisRef) {

    }

    private void adjustToolbar(RepositoryVO repository) {
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("PRs of " + repository.getName());
    }

    private void setupViewPager(RepositoryVO repository) {
        viewPager.setAdapter(new PullRequestsPagerAdapter(getSupportFragmentManager(), repository));
        tabLayout.setupWithViewPager(viewPager);
    }
}

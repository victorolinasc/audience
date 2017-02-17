package br.com.concretesolutions.audience.sample.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.api.model.RepositoryVO;
import br.com.concretesolutions.audience.sample.ui.adapter.PullRequestsPagerAdapter;
import butterknife.BindView;

public class PullRequestsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private RepositoryVO repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrequests);

        this.repository = getIntent().getExtras().getParcelable("repository");

        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(new PullRequestsPagerAdapter(getSupportFragmentManager(), repository));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

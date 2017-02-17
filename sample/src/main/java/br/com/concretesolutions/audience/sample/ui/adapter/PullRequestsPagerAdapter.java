package br.com.concretesolutions.audience.sample.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.concretesolutions.audience.sample.api.model.RepositoryVO;
import br.com.concretesolutions.audience.sample.ui.fragment.PullRequestFragment;

public class PullRequestsPagerAdapter extends FragmentPagerAdapter {

    private final RepositoryVO repo;

    public PullRequestsPagerAdapter(FragmentManager fm, RepositoryVO repo) {
        super(fm);
        this.repo = repo;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0)
            return PullRequestFragment.newInstance(repo, true);

        else if (position == 1)
            return PullRequestFragment.newInstance(repo, false);

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0
                ? "Open"
                : "Closed";
    }
}

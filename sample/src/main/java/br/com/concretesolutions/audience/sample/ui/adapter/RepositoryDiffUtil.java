package br.com.concretesolutions.audience.sample.ui.adapter;


import android.support.v7.util.DiffUtil;

import java.util.List;

import br.com.concretesolutions.audience.sample.data.api.model.RepositoryVO;

public class RepositoryDiffUtil extends DiffUtil.Callback {

    private final List<RepositoryVO> oldRepositories;
    private final List<RepositoryVO> newRepositories;

    public RepositoryDiffUtil(List<RepositoryVO> oldRepositories, List<RepositoryVO> newRepositories) {
        this.oldRepositories = oldRepositories;
        this.newRepositories = newRepositories;
    }

    @Override
    public int getOldListSize() {
        return oldRepositories.size();
    }

    @Override
    public int getNewListSize() {
        return newRepositories.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRepositories.get(oldItemPosition).getId()
                .equals(newRepositories.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRepositories.get(oldItemPosition)
                .equals(newRepositories.get(newItemPosition));
    }
}

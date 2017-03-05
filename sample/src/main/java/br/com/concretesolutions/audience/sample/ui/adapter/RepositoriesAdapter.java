package br.com.concretesolutions.audience.sample.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.data.api.Api;
import br.com.concretesolutions.audience.sample.data.api.ApiProvider;
import br.com.concretesolutions.audience.sample.data.api.model.PageResultVO;
import br.com.concretesolutions.audience.sample.data.api.model.RepositoryVO;
import br.com.concretesolutions.audience.sample.ui.activity.PullRequestsActivity;
import br.com.concretesolutions.audience.sample.ui.viewholder.ErrorItemViewHolder;
import br.com.concretesolutions.audience.sample.ui.viewholder.LoadingViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class RepositoriesAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int REPO = R.layout.item_repository;

    private final List<RepositoryVO> repositories = new ArrayList<>();
    private final ActorRef activityRef;

    private int pageNo = 1;
    private int errorString = -1;

    public RepositoriesAdapter(ActorRef activityRef) {
        this.activityRef = activityRef;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false);

        if (viewType == REPO)
            return new RepositoryItemViewHolder(view);

        if (viewType == LoadingViewHolder.LAYOUT)
            return new LoadingViewHolder(view);

        return new ErrorItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LoadingViewHolder) {
            holder.itemView.setOnClickListener(null);
            loadRepositories();
            return;
        }

        if (holder instanceof ErrorItemViewHolder) {
            ((ErrorItemViewHolder) holder).bind(errorString, this::loadRepositories);
            return;
        }

        ((RepositoryItemViewHolder) holder).bind(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position != repositories.size())
            return REPO;

        if (errorString != -1)
            return ErrorItemViewHolder.LAYOUT;

        return LoadingViewHolder.LAYOUT;
    }

    public Bundle onSaveInstanceState(Bundle outState) {
        outState.putInt("RepositoriesAdapter.pageNo", pageNo);
        outState.putParcelableArrayList("RepositoriesAdapter.repositories",
                (ArrayList<? extends Parcelable>) repositories);
        return outState;
    }

    public void onRestoreInstanceState(Bundle state) {

        pageNo = state.getInt("RepositoriesAdapter.pageNo");

        final ArrayList<RepositoryVO> repositories = state
                .getParcelableArrayList("RepositoriesAdapter.repositories");

        if (repositories != null)
            this.repositories.addAll(repositories);
    }

    public void addPage(PageResultVO<RepositoryVO> respositoriesPage) {
        final int size = this.repositories.size();
        this.repositories.addAll(respositoriesPage.getItems());
        pageNo++;
        notifyItemRangeInserted(size, respositoriesPage.getItems().size());
    }

    public boolean isFirstPage() {
        return pageNo == 1;
    }

    public void loadRepositories() {

        if (errorString != -1) {
            errorString = -1;
            notifyItemChanged(repositories.size() - 1);
        }

        ApiProvider.getApi()
                .getRepositories("language:java", Api.Sort.stars, pageNo, 30)
                .replyTo(activityRef)
                .tell();
    }

    public boolean handleException(@StringRes int errorString) {

        if (repositories.isEmpty())
            return false;

        this.errorString = errorString;
        notifyItemChanged(repositories.size() - 1);
        return true;
    }

    static class RepositoryItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repository_title)
        TextView repositoryTitle;
        @BindView(R.id.repository_description)
        TextView repositoryDescription;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.created_at)
        TextView createdAt;
        @BindView(R.id.no_of_forks)
        TextView noOfForks;
        @BindView(R.id.no_of_stars)
        TextView noOfStars;

        @BindView(R.id.user_icon)
        ImageView userIcon;

        private RepositoryVO repositoryVO;

        RepositoryItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.root)
        void onClick() {
            final Context context = repositoryTitle.getContext();
            final Intent intent = new Intent(context, PullRequestsActivity.class);
            intent.putExtra("repository", repositoryVO);
            context.startActivity(intent);
        }

        void bind(RepositoryVO repositoryVO) {

            this.repositoryVO = repositoryVO;

            repositoryTitle.setText(repositoryVO.getName());
            repositoryDescription.setText(repositoryVO.getDescription());
            username.setText(repositoryVO.getOwner().getLogin());
            createdAt.setText(repositoryVO.getCreatedAt());
            noOfForks.setText(String.valueOf(repositoryVO.getForksCount()));
            noOfStars.setText(String.valueOf(repositoryVO.getStargazersCount()));

            Picasso.with(userIcon.getContext())
                    .load(repositoryVO.getOwner().getAvatarUrl())
                    .into(userIcon);
        }
    }
}

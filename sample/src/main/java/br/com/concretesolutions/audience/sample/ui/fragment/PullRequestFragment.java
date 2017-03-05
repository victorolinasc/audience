package br.com.concretesolutions.audience.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.retrofit.exception.ClientException;
import br.com.concretesolutions.audience.retrofit.exception.NetworkException;
import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.data.api.model.PullRequestVO;
import br.com.concretesolutions.audience.sample.data.api.model.RepositoryVO;
import br.com.concretesolutions.audience.sample.ui.adapter.PullRequestsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PullRequestFragment extends BaseFragment implements Actor {

    public static PullRequestFragment newInstance(RepositoryVO repo, boolean open) {
        final PullRequestFragment fragment = new PullRequestFragment();
        final Bundle bundle = new Bundle();
        bundle.putBoolean("open", open);
        bundle.putParcelable("repo", repo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.pullrequests_list)
    RecyclerView pullRequestsList;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.state_flipper)
    ViewFlipper stateFlipper;

    private PullRequestsAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_pullrequests, container, false);
        ButterKnife.bind(this, view);

        final boolean open = getArguments().getBoolean("open");
        final RepositoryVO repo = getArguments().getParcelable("repo");

        adapter = new PullRequestsAdapter(Director.actorRef(this), open, repo);
        layoutManager = new LinearLayoutManager(getContext());

        pullRequestsList.setLayoutManager(layoutManager);
        pullRequestsList.setAdapter(adapter);

        if (savedInstanceState == null)
            adapter.loadRepositories();

        else {
            layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable("layoutManager"));
            adapter.onRestoreInstanceState(savedInstanceState);

            if (!adapter.isFirstPage())
                stateFlipper.setDisplayedChild(1);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("layoutManager", layoutManager.onSaveInstanceState());
        adapter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActorRegistered(ActorRef thisRef) {
        thisRef.passScript(ArrayList.class, this::handleResponse)
                .passScript(ClientException.class, this::handleClientException)
                .passScript(NetworkException.class, this::handleNetworkException);
    }

    private void handleResponse(List<PullRequestVO> response, ActorRef sender, ActorRef self) {

        if (response.size() == 0 && adapter.isFirstPage()) {
            stateFlipper.setDisplayedChild(2);
            return;
        }

        // flip from loading
        if (stateFlipper.getCurrentView() == loading)
            stateFlipper.setDisplayedChild(1);

        adapter.processResponse(response);
    }
}

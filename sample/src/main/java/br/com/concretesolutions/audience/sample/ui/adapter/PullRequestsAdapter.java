package br.com.concretesolutions.audience.sample.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import io.github.victorolinasc.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.data.api.Api;
import br.com.concretesolutions.audience.sample.data.api.ApiProvider;
import br.com.concretesolutions.audience.sample.data.api.model.PullRequestVO;
import br.com.concretesolutions.audience.sample.data.api.model.RepositoryVO;
import br.com.concretesolutions.audience.sample.ui.activity.PullRequestsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class PullRequestsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int
            LOADING = R.layout.item_loading,
            PR = R.layout.item_pullrequest;

    private final List<PullRequestVO> pullRequests = new ArrayList<>();
    private final ActorRef fragmentRef;

    private final boolean open;
    private final RepositoryVO repo;
    private final Parser parser;
    private final HtmlRenderer renderer;

    private int pageNo = 1;
    private boolean noMorePages = false;

    public PullRequestsAdapter(ActorRef fragmentRef, boolean open, RepositoryVO repo) {
        this.fragmentRef = fragmentRef;
        this.open = open;
        this.repo = repo;

        final List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),
                AutolinkExtension.create(),
                StrikethroughExtension.create(),
                HeadingAnchorExtension.create()
        );

        this.parser = Parser.builder().extensions(extensions).build();
        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .nodeRendererFactory(IndentedCodeBlockNodeRenderer::new)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(viewType, parent, false);

        return viewType == PR
                ? new PullRequestItemViewHolder(view, renderer, parser)
                : new LoadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LoadingViewHolder) {
            holder.itemView.setOnClickListener(null);
            loadRepositories();
            return;
        }

        ((PullRequestItemViewHolder) holder).bind(pullRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return noMorePages ? pullRequests.size() : pullRequests.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == pullRequests.size() ? LOADING : PR;
    }

    public Bundle onSaveInstanceState(Bundle outState) {
        outState.putInt("PullRequestsAdapter.pageNo", pageNo);
        outState.putBoolean("PullRequestsAdapter.noMorePages", noMorePages);
        outState.putParcelableArrayList("PullRequestsAdapter.pullRequests",
                (ArrayList<? extends Parcelable>) pullRequests);
        return outState;
    }

    public void onRestoreInstanceState(Bundle state) {

        pageNo = state.getInt("PullRequestsAdapter.pageNo");
        noMorePages = state.getBoolean("PullRequestsAdapter.noMorePages");

        final ArrayList<PullRequestVO> repositories = state
                .getParcelableArrayList("PullRequestsAdapter.pullRequests");

        if (repositories != null)
            this.pullRequests.addAll(repositories);
    }

    public void processResponse(List<PullRequestVO> prList) {

        if (prList.isEmpty()) {
            noMorePages = true;
            notifyItemRangeRemoved(pullRequests.size() - 1, 1);
            return;
        }

        final int size = pullRequests.size();
        this.pullRequests.addAll(prList);
        pageNo++;
        notifyItemRangeInserted(size, prList.size());
    }

    public boolean isFirstPage() {
        return pageNo == 1;
    }

    public void loadRepositories() {
        ApiProvider.getApi()
                .getPullRequests(
                        repo.getOwner().getLogin(),
                        repo.getName(),
                        Api.State.isOpen(open),
                        pageNo)
                .replyTo(fragmentRef)
                .tell();
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class PullRequestItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pullrequest_title)
        TextView pullRequestTitle;
        @BindView(R.id.pullrequest_body)
        TextView pullrequestBody;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.created_at)
        TextView createdAt;

        @BindView(R.id.user_icon)
        ImageView userIcon;

        private final HtmlRenderer renderer;
        private final Parser parser;

        private PullRequestVO repositoryVO;

        PullRequestItemViewHolder(View itemView, HtmlRenderer renderer, Parser parser) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.renderer = renderer;
            this.parser = parser;
        }

        @OnClick(R.id.root)
        void onClick() {
            final Context context = pullRequestTitle.getContext();
            final Intent intent = new Intent(context, PullRequestsActivity.class);
            intent.putExtra("repository", repositoryVO);
            context.startActivity(intent);
        }

        void bind(PullRequestVO pullRequestVO) {

            this.repositoryVO = pullRequestVO;

            pullRequestTitle.setText(pullRequestVO.getTitle());

            final String htmlBody = renderer.render(parser.parse(pullRequestVO.getBody()));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                pullrequestBody.setText(Html.fromHtml(htmlBody, Html.FROM_HTML_MODE_LEGACY));
            } else {
                //noinspection deprecation
                pullrequestBody.setText(Html.fromHtml(htmlBody));
            }

            username.setText(pullRequestVO.getUser().getLogin());
            createdAt.setText(pullRequestVO.getCreatedAt());

            Picasso.with(userIcon.getContext())
                    .load(pullRequestVO.getUser().getAvatarUrl())
                    .into(userIcon);
        }
    }

    static class IndentedCodeBlockNodeRenderer implements NodeRenderer {

        private final HtmlWriter html;

        IndentedCodeBlockNodeRenderer(HtmlNodeRendererContext context) {
            this.html = context.getWriter();
        }

        @Override
        public Set<Class<? extends Node>> getNodeTypes() {
            // Return the node types we want to use this renderer for.
            return Collections.singleton(IndentedCodeBlock.class);
        }

        @Override
        public void render(Node node) {
            // We only handle one type as per getNodeTypes, so we can just cast it here.
            IndentedCodeBlock codeBlock = (IndentedCodeBlock) node;
            html.line();
            html.tag("pre");
            html.text(codeBlock.getLiteral());
            html.tag("/pre");
            html.line();
        }
    }
}

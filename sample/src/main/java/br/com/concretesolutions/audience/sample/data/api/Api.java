package br.com.concretesolutions.audience.sample.data.api;

import java.util.List;

import br.com.concretesolutions.audience.retrofit.calladapter.MessageCall;
import br.com.concretesolutions.audience.sample.data.api.model.PageResultVO;
import br.com.concretesolutions.audience.sample.data.api.model.PullRequestVO;
import br.com.concretesolutions.audience.sample.data.api.model.RepositoryVO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    enum Sort {
        stars
    }

    enum State {
        open,
        closed;

        public static State isOpen(boolean isOpen) {
            return isOpen ? open : closed;
        }
    }

    @GET("search/repositories")
    MessageCall<PageResultVO<RepositoryVO>> getRepositories(
            @Query("q") String languageKeyValue,
            @Query("sort") Sort sort,
            @Query("page") int page,
            @Query("per_page") int perPage);

    @GET("repos/{owner}/{repo}/pulls")
    MessageCall<List<PullRequestVO>> getPullRequests(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Query("state") State state,
            @Query("page") int page
    );
}

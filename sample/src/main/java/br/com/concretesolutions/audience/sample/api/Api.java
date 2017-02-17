package br.com.concretesolutions.audience.sample.api;

import java.util.List;

import br.com.concretesolutions.audience.sample.api.model.PageResultVO;
import br.com.concretesolutions.audience.sample.api.model.PullRequestVO;
import br.com.concretesolutions.audience.sample.api.model.RepositoryVO;
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
    Call<PageResultVO<RepositoryVO>> getRepositories(
            @Query("q") String languageKeyValue,
            @Query("sort") Sort sort,
            @Query("page") Integer page);

    @GET("repos/{owner}/{repo}/pulls")
    Call<List<PullRequestVO>> getPullRequests(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Query("state") State state,
            @Query("page") int page
    );
}

package br.com.concretesolutions.audience.demo.api;

import br.com.concretesolutions.audience.demo.api.model.PageResultVO;
import br.com.concretesolutions.audience.demo.api.model.RepositoryVO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("search/repositories")
    Call<PageResultVO<RepositoryVO>> getRepositories(
            @Query("q") String languageKeyValue,
            @Query("sort") Sort sort,
            @Query("page") Integer page);

}

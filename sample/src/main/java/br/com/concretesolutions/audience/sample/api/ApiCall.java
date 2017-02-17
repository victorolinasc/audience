package br.com.concretesolutions.audience.sample.api;

import retrofit2.Call;

public interface ApiCall<T> {
    Call<T> call(Api api);
}

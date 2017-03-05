package br.com.concretesolutions.audience.retrofit;

import retrofit2.Call;

public interface ApiCall<T, R> {
    Call<R> call(T api);
}

package br.com.concretesolutions.audience.sample.data.api;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import br.com.concretesolutions.audience.retrofit.calladapter.AudienceCallAdapterFactory;
import br.com.concretesolutions.audience.sample.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class ApiProvider {

    private static final ApiProvider INSTANCE = new ApiProvider();

    private Api api;
    private boolean isInitialized = false;

    private ApiProvider() {
    }

    @NonNull
    public static Api getApi() {

        if (!INSTANCE.isInitialized)
            throw new IllegalStateException("Must call ApiProvider.init(url) first");

        return INSTANCE.api;
    }

    public static void init(@NonNull String baseUrl) {
        INSTANCE.initInternal(baseUrl);
    }

    private void initInternal(@NonNull String baseUrl) {
        final OkHttpClient client = getOkHttpClient();
        final Retrofit retrofit = getRetrofit(baseUrl, client);
        isInitialized = true;
        this.api = retrofit.create(Api.class);
    }

    @NonNull
    private Retrofit getRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(AudienceCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @NonNull
    private OkHttpClient getOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .connectTimeout(15L, TimeUnit.SECONDS)
                .readTimeout(15L, TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS)
                .followSslRedirects(false);

        if (BuildConfig.DEBUG)
            builder.addNetworkInterceptor(new StethoInterceptor());

        return builder.build();
    }
}

package br.com.concretesolutions.audience.sample.api;

import java.io.IOException;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.sample.api.exception.ClientException;
import br.com.concretesolutions.audience.sample.api.exception.NetworkException;
import br.com.concretesolutions.audience.sample.api.exception.ServerException;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class ApiManager implements Actor {

    public static <T> void sendCall(ActorRef sender, ApiCall<T> call) {
        sender.tell(call).toActor(ApiManager.class);
    }

    public static <T> void sendCall(Actor sender, ApiCall<T> call) {
        Director.actorRef(sender)
                .tell(call)
                .toActor(ApiManager.class);
    }

    private final Api api;

    public ApiManager(String baseUrl) {

        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)
                ).build();

        this.api = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(Api.class);
    }

    @Override
    public void onActorRegistered(ActorRef thisRef) {
        thisRef.passScript(ApiCall.class, this::handleCall);
    }

    private <T> void handleCall(ApiCall<T> call, ActorRef sender, ActorRef self) {

        final Call<T> result = call.call(api);

        try {
            final Response<T> response = result.execute();

            if (response.isSuccessful()) {
                self.tell(response.body()).onStage().to(sender);
                return;
            }

            final int code = response.code();
            final String body = response.errorBody().string();

            if (code >= 400 && code < 500)
                self.tell(new ClientException(code, body))
                        .onStage()
                        .to(sender);

            else
                self.tell(new ServerException(code, body))
                        .onStage()
                        .to(sender);

        } catch (IOException e) {
            self.tell(new NetworkException("IO exception on API call", e))
                    .onStage()
                    .to(sender);
        }
    }
}

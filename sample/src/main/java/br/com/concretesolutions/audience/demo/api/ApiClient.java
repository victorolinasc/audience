package br.com.concretesolutions.audience.demo.api;

import java.io.IOException;

import br.com.concretesolutions.audience.core.actor.Actor;
import br.com.concretesolutions.audience.core.actor.ActorRef;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Singleton actor responsible for API calls
 */
public final class ApiClient implements Actor {

    public interface ApiCall {
        void call(Api api, ActorRef self, ActorRef sender) throws IOException;
    }

    private final Api api;

    public ApiClient() {

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        this.api = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(Api.class);
    }

    @Override
    public void onActorRegistered(ActorRef ref) {
        ref.passScript(ApiCall.class, this::executeCall)
                .passAssistantScript("load", this::showLoading);
    }

    void showLoading(ActorRef sender, ActorRef self) {
        // show loading
    }

    public void executeCall(ApiCall message, ActorRef sender, ActorRef self) {

        try {
            message.call(api, self, sender);
        } catch (IOException e) {
            self.tell(e)
                    .to(sender);
        }
    }
}

package br.com.concretesolutions.audience.demo.api;

import java.io.IOException;

import br.com.concretesolutions.audience.actor.ActorUtils;
import br.com.concretesolutions.audience.system.Actor;
import br.com.concretesolutions.audience.system.ActorRef;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

/**
 * Singleton actor responsible for API calls
 */
public final class ApiClient extends Actor {

    public interface ApiCall {
        void call(Api api, ActorRef sender) throws IOException;
    }

    // Initialization on demand thread-safe Singleton pattern
    // https://en.wikipedia.org/wiki/Singleton_pattern#Initialization-on-demand_holder_idiom
    private static class SingletonHolder {

        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final Api api;

    public ApiClient() {

        Timber.d("Creating ApiClient");
        final HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.i(message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        this.api = retrofit.create(Api.class);
    }

    @Override
    public void onReceive(Object message, int discriminator, ActorRef sender) {

        Timber.d("ApiClient got message %s", message);

        ActorUtils.assertIsNotOnStage(sender);
        ActorUtils.assertMessageIsOfType(message, ApiCall.class, sender);

        final ApiCall call = (ApiCall) message;

        try {
            Timber.d("Calling api");
            call.call(api, sender);
        } catch (Exception e) {
            // reply
            Timber.e(e, "Failed to call API");
        }
    }
}

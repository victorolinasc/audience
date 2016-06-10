package br.com.concretesolutions.audience.demo.api;

import br.com.concretesolutions.audience.actor.ActorUtils;
import br.com.concretesolutions.audience.system.Actor;
import br.com.concretesolutions.audience.system.ActorRef;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Singleton actor responsible for API calls
 */
public final class ApiClient extends Actor {

    public interface ApiCall {
        void call(Api api);
    }

    // Initialization on demand thread-safe Singleton pattern
    // https://en.wikipedia.org/wiki/Singleton_pattern#Initialization-on-demand_holder_idiom
    private static class SingletonHolder {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ApiClient() {

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        new Retrofit.Builder()
                .baseUrl("https://api.github.com/search/repositories")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Override
    public void onReceive(Object message, int discriminator, ActorRef sender) {

        ActorUtils.assertIsNotOnStage(sender);
        ActorUtils.assertMessageIsOfType(message, ApiClient.class, sender);


    }
}

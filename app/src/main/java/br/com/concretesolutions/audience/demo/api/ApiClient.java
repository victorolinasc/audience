package br.com.concretesolutions.audience.demo.api;

import android.content.Context;

import java.io.IOException;

import br.com.concretesolutions.audience.Director;
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
        void call(Api api, ActorRef ref) throws IOException;
    }

    private final Api api;

    public ApiClient(Context context) {

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
    public void onReceive(Object message, int discriminator, ActorRef sender) {
        ActorUtils.assertIsNotOnStage(sender);
        ActorUtils.assertMessageIsOfType(message, ApiCall.class, sender);

        try {
            ((ApiCall) message).call(api, sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

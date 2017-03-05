package br.com.concretesolutions.audience.sample;

import android.app.Application;
import android.content.res.Configuration;

import com.facebook.stetho.Stetho;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.rule.LoggingShowRule;
import br.com.concretesolutions.audience.retrofit.RetrofitActor;
import br.com.concretesolutions.audience.sample.data.api.ApiProvider;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Director.beginShow(this)
                .toRuleRegistry()
                .addAssistantAndScriptRule(new LoggingShowRule())
                .toActorRegistry()
                .enrollSingleton(new RetrofitActor());

        ApiProvider.init("https://api.github.com");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Director.onConfigurationChanged();
        super.onConfigurationChanged(newConfig);
    }
}

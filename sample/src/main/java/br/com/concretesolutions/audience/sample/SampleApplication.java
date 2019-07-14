package br.com.concretesolutions.audience.sample;

import android.app.Application;
import android.content.res.Configuration;

import com.facebook.stetho.Stetho;

import io.github.victorolinasc.audience.core.Director;
import io.github.victorolinasc.audience.core.rule.LoggingShowRule;
import br.com.concretesolutions.audience.sample.data.api.ApiProvider;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        Director.INSTANCE.beginShow(this)
                .getRuleRegistry()
                .addAssistantAndScriptRule(new LoggingShowRule());

        ApiProvider.init("https://api.github.com");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Director.INSTANCE.onConfigurationChanged();
        super.onConfigurationChanged(newConfig);
    }
}

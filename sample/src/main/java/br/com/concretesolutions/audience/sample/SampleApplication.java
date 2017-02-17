package br.com.concretesolutions.audience.sample;


import android.app.Application;
import android.content.res.Configuration;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.rule.LoggingShowRule;
import br.com.concretesolutions.audience.sample.api.ApiManager;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Director.beginShow(this)
                .enrollSingelton(new ApiManager("https://api.github.com/"));

        Director.getRuleRegistry()
                .addAssistantAndScriptRule(new LoggingShowRule());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Director.onConfigurationChanged();
        super.onConfigurationChanged(newConfig);
    }
}

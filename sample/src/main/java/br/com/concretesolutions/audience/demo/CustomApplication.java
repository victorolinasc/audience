package br.com.concretesolutions.audience.demo;

import android.app.Application;
import android.content.res.Configuration;

import br.com.concretesolutions.audience.core.Director;
import br.com.concretesolutions.audience.core.rule.LoggingShowRule;
import br.com.concretesolutions.audience.demo.api.ApiClient;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Director.beginShow(this)
                .enroll(this, new ApiClient());

        Director.getRuleRegistry()
                .addAssistantAndScriptRule(new LoggingShowRule());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Director.onConfigurationChanged();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        Director.endShow();
        super.onTerminate();
    }
}

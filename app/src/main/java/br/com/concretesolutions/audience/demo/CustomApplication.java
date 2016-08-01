package br.com.concretesolutions.audience.demo;

import android.app.Application;
import android.content.res.Configuration;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.demo.api.ApiClient;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Director
                .beginShow(this)
                .staffRegistry()
                .registerRole("/app/" + ApiClient.class.getCanonicalName(), ApiClient.class);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Director.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Director.endShow();
    }
}

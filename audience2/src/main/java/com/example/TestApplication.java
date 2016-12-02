package com.example;

import android.app.Application;
import android.content.res.Configuration;

import br.com.concretesolutions.audience.core.Director;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Director.beginShow(this);

        final ApiClient apiClient = new ApiClient();
        Director.register(apiClient);

        Director.register(new ApiClient());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Director.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Director.endShow(this);
    }
}

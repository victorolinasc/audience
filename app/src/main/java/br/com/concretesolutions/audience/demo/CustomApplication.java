package br.com.concretesolutions.audience.demo;

import android.app.Application;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.demo.api.ApiClient;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Director.beginShow(this);
        Director.crewSystem().staffRegistry().registerRole("/apiclient", ApiClient.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Director.endShow();
    }
}

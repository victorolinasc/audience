package br.com.concretesolutions.audience.demo;

import android.app.Application;
import android.os.Message;

import br.com.concretesolutions.audience.Director;
import br.com.concretesolutions.audience.demo.api.ApiClient;
import br.com.concretesolutions.audience.staff.Filter;
import timber.log.Timber;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Director.beginShow(this);
        Director.crewSystem()
                .staffRegistry()
                .registerRole("/app/apiclient", ApiClient.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Director.endShow();
    }
}

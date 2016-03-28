package br.com.concretesolutions.audience.actor;

import android.util.Log;

import br.com.concretesolutions.audience.system.Actor;
import br.com.concretesolutions.audience.system.ActorRef;

public class ActivityActor extends Actor {

    private static final String TAG = ActivityActor.class.getSimpleName();
    private static final String FORMAT = "Info\nReceived message: %s\nIn thread: %s\nFrom sender: %s";

    @Override
    public void onReceive(Object message, int discriminator, ActorRef sender) {
        Log.i(TAG, String.format(FORMAT, message, Thread.currentThread(), sender));
    }
}

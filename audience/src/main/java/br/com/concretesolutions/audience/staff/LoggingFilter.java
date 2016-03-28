package br.com.concretesolutions.audience.staff;

import android.os.Message;
import android.util.Log;

public class LoggingFilter implements Filter {

    private static final String TAG = LoggingFilter.class.getSimpleName();

    @Override
    public Message intercept(Message message) {
        Log.i(TAG, "Intercepted message: " + message);
        return message;
    }
}

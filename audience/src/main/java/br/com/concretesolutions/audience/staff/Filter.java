package br.com.concretesolutions.audience.staff;

import android.os.Message;

public interface Filter {
    Message intercept(Message message);
}

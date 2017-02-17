package br.com.concretesolutions.audience.sample.ui.fragment;


import android.widget.Toast;

import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.core.actor.SupportFragmentActor;
import br.com.concretesolutions.audience.sample.api.exception.ApiException;

public abstract class BaseFragment extends SupportFragmentActor {

    void handleNetworkException(ApiException page, ActorRef sender, ActorRef self) {
        Toast.makeText(getContext(), "Network exception", Toast.LENGTH_SHORT).show();
    }

    void handleClientException(ApiException page, ActorRef sender, ActorRef self) {
        Toast.makeText(getContext(), "Client exception", Toast.LENGTH_SHORT).show();
    }
}

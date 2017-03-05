package br.com.concretesolutions.audience.sample.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.retrofit.exception.ApiException;

public abstract class BaseFragment extends Fragment {

    void handleNetworkException(ApiException page, ActorRef sender, ActorRef self) {
        Toast.makeText(getContext(), "Network exception", Toast.LENGTH_SHORT).show();
    }

    void handleClientException(ApiException page, ActorRef sender, ActorRef self) {
        Toast.makeText(getContext(), "Client exception", Toast.LENGTH_SHORT).show();
    }
}

package br.com.concretesolutions.audience.sample.ui.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.retrofit.exception.ClientException;
import br.com.concretesolutions.audience.retrofit.exception.NetworkException;
import br.com.concretesolutions.audience.retrofit.exception.ServerException;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    ActorRef addExceptionScripts(ActorRef ref) {
        return ref.passScript(ClientException.class, this::handleClientException)
                .passScript(ServerException.class, this::handleServerException)
                .passScript(NetworkException.class, this::handleNetworkException)
                .passAssistantScript("try_again", this::tryAgain);
    }

    void tryAgain() {
    }

    void handleClientException(ClientException exception, ActorRef sender, ActorRef self) {
    }

    void handleServerException(ServerException exception, ActorRef sender, ActorRef self) {
    }

    void handleNetworkException(NetworkException exception, ActorRef sender, ActorRef self) {
    }

}

package br.com.concretesolutions.audience.sample.ui.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ViewFlipper;

import br.com.concretesolutions.audience.core.actor.ActorRef;
import br.com.concretesolutions.audience.sample.R;
import br.com.concretesolutions.audience.sample.api.exception.ClientException;
import br.com.concretesolutions.audience.sample.api.exception.NetworkException;
import br.com.concretesolutions.audience.sample.api.exception.ServerException;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

}

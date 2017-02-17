package br.com.concretesolutions.audience.sample.ui.viewholder;


import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.concretesolutions.audience.sample.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorItemViewHolder extends RecyclerView.ViewHolder {

    @LayoutRes
    public static final int LAYOUT = R.layout.item_error;

    public interface OnRetryListener {
        void onRetry();
    }

    @BindView(R.id.error_message)
    TextView errorMessage;

    @BindView(R.id.try_again_button)
    ImageButton tryAgain;

    public ErrorItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@StringRes int errorString, OnRetryListener retryListener) {
        errorMessage.setText(errorString);
        tryAgain.setOnClickListener(v -> retryListener.onRetry());
    }
}

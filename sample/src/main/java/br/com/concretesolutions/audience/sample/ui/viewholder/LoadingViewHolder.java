package br.com.concretesolutions.audience.sample.ui.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import br.com.concretesolutions.audience.sample.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {

    @LayoutRes
    public static final int LAYOUT = R.layout.item_loading;

    public LoadingViewHolder(View itemView) {
        super(itemView);
    }
}

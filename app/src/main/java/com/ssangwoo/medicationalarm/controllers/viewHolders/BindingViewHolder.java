package com.ssangwoo.medicationalarm.controllers.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ssangwoo on 2017-12-04.
 */

public abstract class BindingViewHolder<T> extends RecyclerView.ViewHolder {
    public BindingViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(T item);
}

package com.ssangwoo.medicationalarm.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ssangwoo on 2017-11-12.
 */

public abstract class ViewTypeBinder<T> extends RecyclerView.ViewHolder {
    public ViewTypeBinder(View itemView) {
        super(itemView);
    }
    abstract void bind(T item);
}

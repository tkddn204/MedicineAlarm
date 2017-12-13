package com.ssangwoo.medicationalarm.lib;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Ivan Prymak
 */

public class ObserverableAdapter <VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements FlowContentObserver.OnModelStateChangedListener {

    protected List<? extends BaseModel> dataList;
    protected FlowContentObserver mObserver;
    private Class<? extends BaseModel> observableModel;

    public ObserverableAdapter(Class<? extends BaseModel> observableModel){
        this.observableModel = observableModel;
        mObserver = new FlowContentObserver(new Handler(Looper.getMainLooper()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mObserver.registerForContentChanges(recyclerView.getContext(), observableModel);
        mObserver.addModelChangeListener(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mObserver.unregisterForContentChanges(recyclerView.getContext());
        mObserver.removeModelChangeListener(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onModelStateChanged(@Nullable Class<?> table, BaseModel.Action action,
                                    @NonNull SQLOperator[] primaryKeyValues) {
        dataList = new Select().from(observableModel).queryList();
        this.notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
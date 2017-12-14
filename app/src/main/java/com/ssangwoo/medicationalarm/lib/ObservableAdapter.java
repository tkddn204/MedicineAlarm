package com.ssangwoo.medicationalarm.lib;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by Ivan Prymak
 */

public class ObservableAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<? extends BaseModel> dataList;
    protected FlowContentObserver mObserver;
    private Class<? extends BaseModel> observableModel;

    private FlowContentObserver.OnModelStateChangedListener listener;

    public ObservableAdapter(Class<? extends BaseModel> observableModel){
        this.observableModel = observableModel;
        mObserver = new FlowContentObserver(new Handler(Looper.getMainLooper()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mObserver.registerForContentChanges(recyclerView.getContext(), observableModel);
        mObserver.addModelChangeListener(listener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mObserver.unregisterForContentChanges(recyclerView.getContext());
        mObserver.removeModelChangeListener(listener);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void setListener(FlowContentObserver.OnModelStateChangedListener listener) {
        this.listener = listener;
    }

    public void setDataList(List<? extends BaseModel> dataList) {
        this.dataList = dataList;
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
package com.ssangwoo.medicationalarm;

import android.app.Application;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by ssangwoo on 2017-10-27.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}

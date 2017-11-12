package com.ssangwoo.medicationalarm.controllers;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class MedicineAlarmService extends Service {
    private final static int VIBRATE_DELAY = 2000;
    private final static int VIBRATE = 2000;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    public MedicineAlarmService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread(getClass().getName());
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
}

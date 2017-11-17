package com.ssangwoo.medicationalarm.alarms;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Vibrator;

import com.ssangwoo.medicationalarm.views.activities.MainActivity;

public class AlarmService extends Service {
    // Time period between two vibration events
    private final static int VIBRATE_DELAY_TIME = 2000;
    // Vibrate for 1000 milliseconds
    private final static int DURATION_OF_VIBRATION = 1000;
    // Increase alarm volume gradually every 600ms
    private final static int VOLUME_INCREASE_DELAY = 600;
    // Volume level increasing step
    private final static float VOLUME_INCREASE_STEP = 0.01f;
    // Max player volume level
    private final static float MAX_VOLUME = 1.0f;

    private MediaPlayer mPlayer;
    private Vibrator mVibrator;
    private float mVolumeLevel = 0;

    private Handler alarmThreadHandler = new Handler();
    private Runnable vibrationRunnable = new Runnable() {
        @Override
        public void run() {
            mVibrator.vibrate(DURATION_OF_VIBRATION);
            // Provide loop for vibration
            alarmThreadHandler.postDelayed(vibrationRunnable,
                    DURATION_OF_VIBRATION + VIBRATE_DELAY_TIME);
        }
    };

    private Runnable volumeRunnable = new Runnable() {
        @Override
        public void run() {
            // increase volume level until reach max value
            if (mPlayer != null && mVolumeLevel < MAX_VOLUME) {
                mVolumeLevel += VOLUME_INCREASE_STEP;
                mPlayer.setVolume(mVolumeLevel, mVolumeLevel);
                // set next increase in 600ms
                alarmThreadHandler.postDelayed(volumeRunnable, VOLUME_INCREASE_DELAY);
            }
        }
    };

    private MediaPlayer.OnErrorListener errorListener
            = new MediaPlayer.OnErrorListener(){
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            mediaPlayer.stop();
            mediaPlayer.release();
            alarmThreadHandler.removeCallbacksAndMessages(null);
            AlarmService.this.stopSelf();
            return true;
        }
    };

    @Override
    public void onCreate() {
        HandlerThread ht = new HandlerThread("alarm_service");
        ht.start();
        alarmThreadHandler = new Handler(ht.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startPlayer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        alarmThreadHandler.removeCallbacksAndMessages(null);
    }

    private void startPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnErrorListener(errorListener);

        try {
            // add vibration to alarm alert if it is set
            // if (App.getState().settings().vibrate()) {
            mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            alarmThreadHandler.post(vibrationRunnable);
            //}
            // Player setup is here
            //String ringtone = App.getState().settings().ringtone();
            String ringtone = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    //&& ringtone.startsWith("content://media/external/")
                    && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
            }
            mPlayer.setDataSource(this, Uri.parse(ringtone));
            mPlayer.setLooping(true);
            mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mPlayer.setVolume(mVolumeLevel, mVolumeLevel);
            mPlayer.prepare();
            mPlayer.start();

//            if (App.getState().settings().ramping()) {
                alarmThreadHandler.postDelayed(volumeRunnable, VOLUME_INCREASE_DELAY);
//            } else {
//                mPlayer.setVolume(MAX_VOLUME, MAX_VOLUME);
//            }
        } catch (Exception e) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            stopSelf();
        }
    }
}

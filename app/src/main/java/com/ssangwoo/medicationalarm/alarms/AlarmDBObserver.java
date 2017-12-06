package com.ssangwoo.medicationalarm.alarms;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by ssangwoo on 2017-12-05.
 */

public class AlarmDBObserver extends ContentObserver {
    public AlarmDBObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }
}

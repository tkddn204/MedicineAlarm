package com.ssangwoo.medicationalarm.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ssangwoo on 2017-10-31.
 */

public class AppDateFormat {
    public static final SimpleDateFormat DATE_FROM =
            new SimpleDateFormat("yy년MM월dd일 부터", Locale.KOREA);
    public static final SimpleDateFormat DATE_TO =
            new SimpleDateFormat("yy년MM월dd일 까지", Locale.KOREA);
    public static final SimpleDateFormat ALARM_TIME =
            new SimpleDateFormat("매일 a hh:mm 마다", Locale.KOREA);
    public static final long DATE_AFTER_SEVEN_DAYS =
            System.currentTimeMillis() + 518400000L;
}

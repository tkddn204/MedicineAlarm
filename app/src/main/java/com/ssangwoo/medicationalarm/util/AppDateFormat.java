package com.ssangwoo.medicationalarm.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ssangwoo on 2017-10-31.
 */

public class AppDateFormat {
    public static final SimpleDateFormat DATE_FROM =
            new SimpleDateFormat("yyyy년 MM월 dd일부터", Locale.KOREA);
    public static final SimpleDateFormat DATE_TO =
            new SimpleDateFormat("yyyy년 MM월 dd일까지", Locale.KOREA);
}

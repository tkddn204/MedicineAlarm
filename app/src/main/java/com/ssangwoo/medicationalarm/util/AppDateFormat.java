package com.ssangwoo.medicationalarm.util;

import com.ssangwoo.medicationalarm.models.Medicine;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ssangwoo on 2017-10-31.
 */

public class AppDateFormat {
    public static final SimpleDateFormat DATE_FROM =
            new SimpleDateFormat("MM월 dd일부터", Locale.KOREA);
    public static final SimpleDateFormat DATE_TO =
            new SimpleDateFormat("MM월 dd일까지", Locale.KOREA);

    public static final SimpleDateFormat DATE_YEAR_FROM =
            new SimpleDateFormat("YYYY년 MM월 dd일부터", Locale.KOREA);
    public static final SimpleDateFormat DATE_YEAR_TO =
            new SimpleDateFormat("YYYY년 MM월 dd일까지", Locale.KOREA);

    public static final SimpleDateFormat ALARM_TIME =
            new SimpleDateFormat("a hh:mm 마다", Locale.KOREA);

    public static final long DATE_AFTER_SEVEN_DAYS =
            System.currentTimeMillis() + 518400000L;

    public static String buildDateString(Medicine medicine, String split) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        calendar.setTime(medicine.getDateFrom());
        int fromYear = calendar.get(Calendar.YEAR);
        calendar.setTime(medicine.getDateTo());
        int toYear = calendar.get(Calendar.YEAR);

        StringBuilder dateStringBuilder = new StringBuilder();
        if(currentYear == fromYear) {
            dateStringBuilder.append(DATE_FROM.format(medicine.getDateFrom()));
        } else {
            dateStringBuilder.append(DATE_YEAR_FROM.format(medicine.getDateFrom()));
        }
        dateStringBuilder.append(split);
        if(currentYear == toYear) {
            dateStringBuilder.append(DATE_TO.format(medicine.getDateTo()));
        } else {
            dateStringBuilder.append(DATE_YEAR_TO.format(medicine.getDateTo()));
        }

        return dateStringBuilder.toString();
    }
}

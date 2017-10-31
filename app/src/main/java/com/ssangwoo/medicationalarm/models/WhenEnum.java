package com.ssangwoo.medicationalarm.models;

/**
 * Created by ssangwoo on 2017-10-30.
 */

public enum WhenEnum {
    MORNING("아침"),
    AFTERNOON("점심"),
    DINNER("저녁"),
    EMPTY("");

    String when;
    WhenEnum() {}
    WhenEnum(String when) {
        this.when = when;
    }

    public String getWhenName() {
        return when;
    }
}

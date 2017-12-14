package com.ssangwoo.medicationalarm.enums;

import android.support.annotation.IdRes;

import com.ssangwoo.medicationalarm.R;

/**
 * Created by ssangwoo on 2017-10-30.
 */

public enum TakeMedicineEnum {
    NOT_YET_TAKE(android.R.color.transparent),
    TAKE(R.drawable.ic_done_black),
    DO_NOT_TAKE(R.drawable.ic_clear_black);

    private @IdRes int imageRes;

    TakeMedicineEnum(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }
}

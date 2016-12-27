package com.example.chenglongbao.helloworld;

import android.view.View;

import java.util.Calendar;

/**
 * Created by chenglong.bao on 2016/12/14.
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_TIME = 1000;

    public long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if ((currentTime - lastClickTime) > MIN_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }

    }
    public abstract void onNoDoubleClick(View v);
}

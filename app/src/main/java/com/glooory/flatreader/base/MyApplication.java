package com.glooory.flatreader.base;

import android.app.Application;

/**
 * Created by Glooory on 2016/9/28 0028 13:25.
 */

public class MyApplication extends Application {
    public static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}

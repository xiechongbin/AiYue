package com.chexiaoya.aiyue.application;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by xcb on 2019/1/7.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}

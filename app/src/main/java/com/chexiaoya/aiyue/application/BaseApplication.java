package com.chexiaoya.aiyue.application;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.litepal.LitePal;

/**
 * Created by xcb on 2019/1/7.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        initLogger();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(3)
                .methodOffset(7)
                .tag("AI_TAG")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}

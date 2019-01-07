package com.chexiaoya.aiyue.utils;

import android.content.Context;
import android.content.Intent;

import com.chexiaoya.aiyue.activity.MainActivity;

;

/**
 * Created by xcb on 2019/1/3.
 */
public class LaunchUtils {
    /**
     * 启动主页面
     */
    public static void launchMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}

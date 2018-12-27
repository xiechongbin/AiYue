package com.chexiaoya.aiyue.utils;

import android.content.Context;

/**
 * android 相关参数获取工具类
 * Created by xcb on 2018/12/27.
 */
public class AndroidDeviceUtils {
    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int resource_id = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resource_id);
    }
}

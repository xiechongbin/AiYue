package com.chexiaoya.aiyue.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * desc:fragment工具类
 * create at: 2017/3/28 16:29
 * create by: yyw
 */
public class FragUtil {
    /*
    *如果使用的commit方法是在Activity的onSaveInstanceState()之后调用的，这样会出错，因为onSaveInstanceState
    *方法是在该Activity即将被销毁前调用，来保存Activity数据的，如果在保存完状态后再给它添加Fragment就会出错。解决办法就
    *是把commit()方法替换成commitAllowingStateLoss()就行了，其效果是一样的。
     */

    /**
     * 添加fragment
     */
    public static void addFrag(Activity activity, int containerViewID, Fragment fragment, String tag) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.add(containerViewID, fragment, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加fragment
     */
    public static void addFrag(Activity activity, int containerViewID, Fragment fragment, String tag, int enter, int exit) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.setCustomAnimations(enter, exit);
            ft.add(containerViewID, fragment, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换fragment
     */
    public static void replaceFrag(Activity activity, int containerViewID, Fragment fragment, String tag) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.replace(containerViewID, fragment, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换fragment
     */
    public static void replaceFrag(Activity activity, int containerViewID, Fragment fragment, String tag, int enter, int exit) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.setCustomAnimations(enter, exit);
            ft.replace(containerViewID, fragment, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示fragment
     */
    public static void showFrag(Activity activity, Fragment fragment) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.show(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示fragment
     */
    public static void showFrag(Activity activity, Fragment fragment, int enter, int exit) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.setCustomAnimations(enter, exit);
            ft.show(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏fragment
     */
    public static void hideFrag(Activity activity, Fragment fragment) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.hide(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏fragment
     */
    public static void hideFrag(Activity activity, Fragment fragment, int enter, int exit) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.setCustomAnimations(enter, exit);
            ft.hide(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除fragment
     */
    public static void removeFrag(Activity activity, Fragment fragment) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除fragment
     */
    public static void removeFrag(Activity activity, Fragment fragment, int enter, int exit) {
        try {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.setCustomAnimations(enter, exit);
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

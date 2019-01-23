package com.chexiaoya.aiyue.RecyclerView.creater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 上拉加载的辅助类为了匹配所有效果
 * Created by xcb on 2019/1/22.
 */
public abstract class UpPullLoadViewCreator {
    /**
     * 获取下拉刷新的view
     */
    public abstract View getUpPullLoadView(Context context, ViewGroup parent);

    /**
     * 上拉
     */
    public abstract void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus);

    /**
     * 正在加载
     */
    public abstract void onLoading();

    /**
     * 停止加载
     */
    public abstract void onStopLoading();

}

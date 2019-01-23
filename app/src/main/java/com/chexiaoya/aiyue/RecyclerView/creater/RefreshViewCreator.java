package com.chexiaoya.aiyue.RecyclerView.creater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 下拉刷新的辅助类为了匹配所有效果
 * Created by xcb on 2019/1/22.
 */
public abstract class RefreshViewCreator {
    /**
     * 获取下拉刷新的view
     */
    public abstract View getRefreshView(Context context, ViewGroup parent);

    /**
     * 下拉
     */
    public abstract void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus);


    /**
     * 正在刷新
     */
    public abstract void onRefreshing();

    /**
     * 停止刷新
     */
    public abstract void onStopRefresh();


    /**
     * 回弹成功回调
     */
    public abstract void onPushBack();

}

package com.chexiaoya.aiyue.view.dataloading;

import android.view.View;

/**
 * 空数据视图回调接口
 * Created by xcb on 2019/1/19.
 */
public interface ViewStateInterface {

    /**
     * 获取空视图实体
     */
    View getView();

    /**
     * 设置提示信息
     */
    void setPromptMessage(String msg);

    /**
     * 设置重试监听
     */
    void setOnRetryListener(OnRetryListener listener);

    /**
     * View是否显示(有的时候可能会启动一些动画，可以在这里做开始停止的动作)
     */
    void visible(boolean visible);
}

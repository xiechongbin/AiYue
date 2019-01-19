package com.chexiaoya.aiyue.view.dataloading;

/**
 * 加载数据显示的状态
 * Created by xcb on 2019/1/19.
 */
public enum State {
    /**
     * 数据内容显示
     */
    CONTENT,

    /**
     * 数据加载中
     */
    ING,

    /**
     * 数据为空
     */
    EMPTY,

    /**
     * 数据加载出错
     */
    ERROR
}

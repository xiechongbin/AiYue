package com.chexiaoya.aiyue.interfaces;

/**
 * Created by xcb on 2019/1/7.
 */
public interface IBase {
    /**
     * 获取总体布局
     *
     * @return layoutID
     */
    int getLayout();

    /**
     * 初始化视图
     */
    void initView(Object obj);

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 设置监听器
     */
    void setListener();
}

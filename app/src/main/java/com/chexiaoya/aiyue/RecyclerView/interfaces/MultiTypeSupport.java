package com.chexiaoya.aiyue.RecyclerView.interfaces;

/**
 * 多布局支持接口
 * Created by xcb on 2019/1/21.
 */
public interface MultiTypeSupport<T> {
    // 根据当前位置或者条目数据返回布局
    int getLayoutId(T item, int position);
}

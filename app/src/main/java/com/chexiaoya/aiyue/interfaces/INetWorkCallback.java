package com.chexiaoya.aiyue.interfaces;

/**
 * Created by xcb on 2019/1/23.
 */
public interface INetWorkCallback<T> {
    void onResponse(T data);

    void onFailure();
}

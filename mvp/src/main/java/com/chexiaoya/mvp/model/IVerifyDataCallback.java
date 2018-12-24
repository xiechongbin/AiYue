package com.chexiaoya.mvp.model;

/**
 * Created by xcb on 2018/12/24.
 */
public interface IVerifyDataCallback<T> {
    void onSuccess(T data);

    void onFailure(String msg);
}

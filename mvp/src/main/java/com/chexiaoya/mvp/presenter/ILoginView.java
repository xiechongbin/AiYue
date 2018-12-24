package com.chexiaoya.mvp.presenter;

/**
 * Created by xcb on 2018/12/24.
 */
public interface ILoginView extends BaseView{

    void onClearText();

    void onLoginSuccess();

    void onLoginFailed(String message);
}

package com.chexiaoya.mvp.presenter;

/**
 * Created by xcb on 2018/12/24.
 */
public class BasePresenter<V extends BaseView> {

    public V view;

    public void attachView(V loginView) {
        this.view = loginView;
    }

    public void detachView() {
        this.view = null;
    }

    public boolean isAttachView() {
        return view != null;
    }


    public V getView() {
        return view;
    }
}

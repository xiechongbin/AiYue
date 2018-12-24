package com.chexiaoya.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chexiaoya.mvp.presenter.BaseView;

/**
 * 基类
 * Created by xcb on 2018/12/24.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onShowView() {

    }

    @Override
    public void onHideView() {

    }
}

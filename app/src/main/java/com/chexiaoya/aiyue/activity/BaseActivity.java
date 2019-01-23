package com.chexiaoya.aiyue.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveMode();
        mRootView = LayoutInflater.from(this).inflate(getLayout(), null, false);
        this.setContentView(mRootView);
        ButterKnife.bind(this);
        initView(savedInstanceState);
        initData();
    }

    /**
     * 设置沉浸式体验
     */
    public void setImmersiveMode() {
        Window window = getWindow();
        View decorView;
        if (Build.VERSION.SDK_INT > 21) {
            if (window != null) {
                decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(option);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();//隐藏标题栏
        }
    }

    public abstract int getLayout();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();


    public View getRootView() {
        return mRootView;
    }
}

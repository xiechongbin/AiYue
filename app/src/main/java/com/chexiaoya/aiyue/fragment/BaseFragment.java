package com.chexiaoya.aiyue.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chexiaoya.aiyue.interfaces.IBase;

/**
 * Created by xcb on 2018/12/29.
 */
public abstract class BaseFragment extends Fragment implements IBase, View.OnTouchListener {
    protected Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    protected void onCheckSaveInstance(Bundle savedInstanceState) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        view.setOnTouchListener(this);
        initView(view);
        onCheckSaveInstance(savedInstanceState);
        initData();
        setListener();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //解决Fragment点击穿透，截断点击事件的传递
        return true;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

}

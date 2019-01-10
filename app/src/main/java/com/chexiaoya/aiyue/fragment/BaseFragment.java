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

import com.chexiaoya.aiyue.interfaces.BackHandledInterface;
import com.chexiaoya.aiyue.interfaces.IBase;

/**
 * Created by xcb on 2018/12/29.
 */
public abstract class BaseFragment extends Fragment implements IBase, View.OnTouchListener {
    protected Activity activity;
    protected BackHandledInterface mBackHandledInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (!(activity instanceof BackHandledInterface)) {
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //告诉FragmentActivity，当前Fragment在栈顶
        mBackHandledInterface.setSelectedFragment(this);
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

    public abstract boolean onBackPressed();
}

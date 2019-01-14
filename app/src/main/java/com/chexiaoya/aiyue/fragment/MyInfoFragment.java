package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;

import com.chexiaoya.aiyue.R;

/**
 * Created by xcb on 2018/12/29.
 */
public class MyInfoFragment extends BaseFragment {

    public static MyInfoFragment newInstance() {
        Bundle args = new Bundle();
        MyInfoFragment fragment = new MyInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_my;
    }

    @Override
    public void initView(Object obj) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}

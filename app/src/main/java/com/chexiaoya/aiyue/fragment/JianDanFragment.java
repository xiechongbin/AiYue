package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;

import com.chexiaoya.aiyue.R;

/**
 * Created by xcb on 2018/12/29.
 */
public class JianDanFragment extends BaseFragment{

    public static JianDanFragment newInstance() {
        Bundle args = new Bundle();
        JianDanFragment fragment = new JianDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_jian_dan;
    }

    @Override
    public void initView(Object obj) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}

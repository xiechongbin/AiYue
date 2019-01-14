package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;

import com.chexiaoya.aiyue.R;

/**
 * Created by xcb on 2018/12/29.
 */
public class VideoFragment extends BaseFragment {

    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_video;
    }

    @Override
    public void initView(Object obj) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}

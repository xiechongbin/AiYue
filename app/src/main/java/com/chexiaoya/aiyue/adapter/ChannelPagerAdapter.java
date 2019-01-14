package com.chexiaoya.aiyue.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.fragment.BaseFragment;
import com.chexiaoya.aiyue.fragment.JianDanFragment;

import java.util.List;

/**
 * Created by xcb on 2019/1/14.
 */
public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<Channel> mChannels;

    public ChannelPagerAdapter(FragmentManager fm, List<Channel> channels) {
        super(fm);
        this.mChannels = channels;
    }

    @Override
    public BaseFragment getItem(int i) {
        return JianDanFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mChannels == null ? 0 : mChannels.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels == null ? "" : mChannels.get(position).getChannelName();
    }
}

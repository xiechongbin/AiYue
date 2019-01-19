package com.chexiaoya.aiyue.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.fragment.BaseFragment;
import com.chexiaoya.aiyue.fragment.DetailsFragment;

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
        return DetailsFragment.newInstance(mChannels.get(i));
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

    /**
     * 更新channel
     */
    public void updateChannels(Channel channel) {
        if (channel.getItemType() == Channel.TYPE_MY_CHANNEL) {
            mChannels.add(channel);
        } else if (channel.getItemType() == Channel.TYPE_ADD_CHANNEL) {
            for (Channel mChannel : mChannels) {
                if (mChannel.getChannelName().equals(channel.getChannelName())) {
                    mChannels.remove(mChannel);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }
}

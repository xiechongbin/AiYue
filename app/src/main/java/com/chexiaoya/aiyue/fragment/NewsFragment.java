package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.adapter.ChannelPagerAdapter;
import com.chexiaoya.aiyue.bean.Channel;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xcb on 2018/12/29.
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.st_newsTab)
    SlidingTabLayout stNewsTab;
    @BindView(R.id.iv_more_channel)
    ImageButton ivMoreChannel;
    Unbinder unbinder;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindArray(R.array.channel)
    String[] channel;
    @BindArray(R.array.channel_params)
    String[] channel_params;
    private ChannelPagerAdapter channelPagerAdapter;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_news;
    }

    @Override
    public void initView(Object obj) {
        View rootView = (View) obj;
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void initData() {
        super.initData();
        initChannels();
        List<Channel> channels = getMyChannels();
        channelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), channels);
        viewPager.setAdapter(channelPagerAdapter);
        stNewsTab.setUnderlineColor(R.color.colorPrimary);
        stNewsTab.setViewPager(viewPager);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setListener() {
        super.setListener();
        ivMoreChannel.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_more_channel) {
            ChannelManagerFragment channelManagerFragment = ChannelManagerFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.channel_select_container,
                    channelManagerFragment,
                    ChannelManagerFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 当为存储数据库的时候 将数据存进数据库
     */
    private void initChannels() {
        int count = LitePal.count(Channel.class);
        if (count <= 0) {//先保存
            for (int i = 0; i < channel.length; i++) {
                Channel c = new Channel();
                if (i == 0) {
                    c.setItemType(Channel.TYPE_MY_CHANNEL);
                    c.setChannelName(channel[i]);
                    c.setChannelSelect(false);
                    c.setChannelType(1);
                    c.setChannelId(channel_params[i]);
                    c.setChannelAdd(true);
                } else {
                    c.setItemType(Channel.TYPE_ADD_CHANNEL);
                    c.setChannelName(channel[i]);
                    c.setChannelSelect(false);
                    c.setChannelType(0);
                    c.setChannelId(channel_params[i]);
                    c.setChannelAdd(false);
                }
                c.save();
            }
        }
    }

    /**
     * 获取我的频道
     */
    private List<Channel> getMyChannels() {
        return LitePal.where("itemType = ?", String.valueOf(Channel.TYPE_MY_CHANNEL)).find(Channel.class);
    }

    /**
     * eventBus 接收
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleChannelsEdit(Channel channel) {
        channelPagerAdapter.updateChannels(channel);
        stNewsTab.notifyDataSetChanged();
    }

}

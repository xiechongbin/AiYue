package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.adapter.ChannelPagerAdapter;
import com.chexiaoya.aiyue.bean.Channel;
import com.flyco.tablayout.SlidingTabLayout;

import org.litepal.LitePal;

import java.util.List;

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
        List<Channel> channels = getMyChannels();
        ChannelPagerAdapter channelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), channels);
        viewPager.setAdapter(channelPagerAdapter);
        stNewsTab.setUnderlineColor(R.color.colorPrimary);
        stNewsTab.setViewPager(viewPager);
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

    private List<Channel> getMyChannels() {
        return LitePal.where("itemType = ?", String.valueOf(Channel.TYPE_MY_CHANNEL)).find(Channel.class);
    }

}

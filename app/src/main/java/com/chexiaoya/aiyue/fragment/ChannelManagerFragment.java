package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.adapter.ChannelManagerAdapter;
import com.chexiaoya.aiyue.adapter.GridSpacingItemDecoration;
import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.utils.AndroidDeviceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 频道管理frag
 * Created by xcb on 2018/12/29.
 */
public class ChannelManagerFragment extends BaseFragment {
    @BindArray(R.array.my_channel)
    String[] my_channel;
    @BindArray(R.array.recommend_channel)
    String[] recommend_channel;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rv_channel)
    RecyclerView rvChannel;
    Unbinder unbinder;
    private ChannelManagerAdapter adapter;


    public static ChannelManagerFragment newInstance() {
        Bundle args = new Bundle();
        ChannelManagerFragment fragment = new ChannelManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_channel_manager;
    }

    @Override
    public void initView(Object obj) {
        View view = (View) obj;
        unbinder = ButterKnife.bind(this, view);
        ViewGroup.LayoutParams layoutParams = ivClose.getLayoutParams();
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, layoutParams.height);
        layoutParams1.topMargin = AndroidDeviceUtils.getStatusBarHeight(getActivity());
        layoutParams1.gravity = Gravity.LEFT;
        layoutParams1.leftMargin = AndroidDeviceUtils.dip2px(getActivity(), 10);
        ivClose.setLayoutParams(layoutParams1);
    }

    @Override
    public void initData() {
        super.initData();
        initRecycleView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                exit();
                break;
        }
    }

    /**
     * 初始化recyclerView
     */
    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), ChannelManagerAdapter.ROW_COUNT);
        adapter = new ChannelManagerAdapter(getChannels(), getActivity());
        rvChannel.addItemDecoration(new GridSpacingItemDecoration(AndroidDeviceUtils.dip2px(getActivity(), 10), true));
        rvChannel.setLayoutManager(adapter.setSpanCount(gridLayoutManager));
        rvChannel.setAdapter(adapter);
    }

    /**
     * 获取channel
     */
    private List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<>();
        Channel channel = new Channel();
        channel.setItemType(Channel.TYPE_MY_CHANNEL_TITLE);
        channel.setChannelName(this.getString(R.string.my_channel));
        channel.setChannelId(0);
        channel.setChannelSelect(true);
        channel.setChannelType(1);
        channels.add(channel);
        int i = 0;
        for (String s : my_channel) {
            i++;
            Channel c = new Channel();
            c.setItemType(Channel.TYPE_MY_CHANNEL);
            c.setChannelName(s);
            c.setChannelId(i);
            c.setChannelSelect(false);
            c.setChannelType(0);
            channels.add(c);
        }
        Channel channel1 = new Channel();
        channel1.setItemType(Channel.TYPE_ADD_CHANNEL_TITLE);
        channel1.setChannelName(this.getString(R.string.add_channel));
        channel1.setChannelId(i++);
        channel1.setChannelSelect(true);
        channel1.setChannelType(1);
        channels.add(channel1);

        for (String s : recommend_channel) {
            i++;
            Channel c = new Channel();
            c.setItemType(Channel.TYPE_ADD_CHANNEL);
            c.setChannelName(s);
            c.setChannelId(i);
            c.setChannelSelect(false);
            c.setChannelType(0);
            channels.add(c);
        }
        return channels;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 退出
     */
    private void exit() {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.anim_page_up, R.anim.anim_page_dowm);
                transaction.remove(this);
                transaction.commitAllowingStateLoss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}

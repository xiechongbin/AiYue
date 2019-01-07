package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.Channel;

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
    private static final int ROW_COUNT = 4;

    @BindArray(R.array.my_channel)
    String[] my_channel;
    @BindArray(R.array.recommend_channel)
    String[] recommend_channel;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rv_channel)
    RecyclerView rvChannel;
    Unbinder unbinder;


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

    @OnClick({R.id.iv_close, R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                break;
            case R.id.tv_edit:
                break;
        }
    }

    /**
     * 初始化recyclerView
     */
    private void initRecycleView() {
        rvChannel.setLayoutManager(new GridLayoutManager(this.getActivity(), ROW_COUNT));
    }

    private List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<>();
        Channel channel = new Channel();
        channel.setItemType(Channel.TYPE_MY_CHANNEL_TITLE);
        channel.setChannelName(this.getString(R.string.my_channel));
        channel.setChannelId(-);
        for (String s : my_channel) {
            Channel channel
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

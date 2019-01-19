package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.adapter.ChannelManagerAdapter;
import com.chexiaoya.aiyue.adapter.divider.GridSpacingItemDecoration;
import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.interfaces.OnChannelChangeListener;
import com.chexiaoya.aiyue.utils.AndroidDeviceUtils;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 频道管理frag
 * Created by xcb on 2018/12/29.
 */
public class ChannelManagerFragment extends BaseFragment implements OnChannelChangeListener {
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
        layoutParams1.topMargin = AndroidDeviceUtils.getStatusBarHeight(activity);
        layoutParams1.gravity = Gravity.START;
        layoutParams1.leftMargin = AndroidDeviceUtils.dip2px(activity, 10);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, ChannelManagerAdapter.ROW_COUNT);
        adapter = new ChannelManagerAdapter(getChannels(), activity, this);
        rvChannel.addItemDecoration(new GridSpacingItemDecoration(AndroidDeviceUtils.dip2px(activity, 10), true));
        rvChannel.setLayoutManager(adapter.setSpanCount(gridLayoutManager));
        rvChannel.setAdapter(adapter);
    }

    /**
     * 获取channel
     */
    private List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<>();
        //头部
        Channel head = new Channel();
        head.setItemType(Channel.TYPE_MY_CHANNEL_TITLE);
        head.setChannelName(this.getString(R.string.my_channel));
        head.setChannelSelect(true);
        head.setChannelType(1);
        channels.add(head);

        List<Channel> lists = LitePal.where("isChannelAdd = ?", "1").find(Channel.class);
        if (lists != null && lists.size() > 0) {
            channels.addAll(lists);
        }
        //头部
        Channel newChannel = new Channel();
        newChannel.setItemType(Channel.TYPE_ADD_CHANNEL_TITLE);
        newChannel.setChannelName(this.getString(R.string.add_channel));
        newChannel.setChannelSelect(true);
        newChannel.setChannelType(1);
        channels.add(newChannel);
        List<Channel> lists1 = LitePal.where("isChannelAdd = ?", "0").find(Channel.class);
        if (lists1 != null && lists1.size() > 0) {
            channels.addAll(lists1);
        }
        return channels;
    }

    @Override
    public void onAddChannel(Channel channel) {
        EventBus.getDefault().post(channel);
    }

    @Override
    public void onRemoveChannel(Channel channel) {
        EventBus.getDefault().post(channel);
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

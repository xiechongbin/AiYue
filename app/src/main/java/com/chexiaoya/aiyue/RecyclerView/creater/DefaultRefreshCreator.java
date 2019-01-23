package com.chexiaoya.aiyue.RecyclerView.creater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.interfaces.OnRefreshListener;

/**
 * 默认的下拉刷新
 * Created by xcb on 2019/1/22.
 */
public class DefaultRefreshCreator extends RefreshViewCreator {
    private View mRefreshView;
    private LinearLayout ll_refresh_tips;
    private RelativeLayout rl_loading;
    private LinearLayout ll_loading_result;
    private TextView tv_head_tips;
    private OnRefreshListener onRefreshListener;

    public DefaultRefreshCreator(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        mRefreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_head, parent, false);
        ll_refresh_tips = mRefreshView.findViewById(R.id.ll_refresh_tips);
        rl_loading = mRefreshView.findViewById(R.id.rl_loading);
        ll_loading_result = mRefreshView.findViewById(R.id.ll_loading_result);
        tv_head_tips = mRefreshView.findViewById(R.id.tv_head_tips);
        return mRefreshView;
    }

    @Override
    public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
        if (ll_refresh_tips.getVisibility() != View.VISIBLE) {
            ll_refresh_tips.setVisibility(View.VISIBLE);
        }
        if (rl_loading.getVisibility() == View.VISIBLE) {
            rl_loading.setVisibility(View.GONE);
        }
        if (ll_loading_result.getVisibility() == View.VISIBLE) {
            ll_loading_result.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void onStopRefresh() {
    }

    @Override
    public void onPushBack() {
        if (ll_refresh_tips.getVisibility() == View.VISIBLE) {
            ll_refresh_tips.setVisibility(View.GONE);
        }
        if (rl_loading.getVisibility() != View.VISIBLE) {
            rl_loading.setVisibility(View.VISIBLE);
        }
        if (ll_loading_result.getVisibility() == View.VISIBLE) {
            ll_loading_result.setVisibility(View.GONE);
        }
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public void updateHeadView(int size) {
        if (ll_refresh_tips.getVisibility() == View.VISIBLE) {
            ll_refresh_tips.setVisibility(View.GONE);
        }
        if (rl_loading.getVisibility() == View.VISIBLE) {
            rl_loading.setVisibility(View.GONE);
        }
        if (ll_loading_result.getVisibility() != View.VISIBLE) {
            ll_loading_result.setVisibility(View.VISIBLE);
        }
        tv_head_tips.setText("为您更新了" + size + "条新闻");

    }

    public View getmRefreshView() {
        return mRefreshView;
    }
}

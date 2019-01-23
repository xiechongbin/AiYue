package com.chexiaoya.aiyue.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.chexiaoya.aiyue.RecyclerView.adapter.WrapRecyclerAdapter;

/**
 * 可以添加头部和底部的RecyclerView
 * Created by xcb on 2019/1/22.
 */
public class WrapRecyclerView extends RecyclerView {
    // 包裹了一层的头部底部Adapter
    private WrapRecyclerAdapter mWrapRecyclerAdapter;
    // 这个是列表数据的Adapter
    private RecyclerView.Adapter mAdapter;

    // 正在加载数据页面，也就是正在获取后台接口页面
    private View mEmptyView, mLoadingView;

    public WrapRecyclerView(@NonNull Context context) {
        super(context);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        // 为了防止多次设置Adapter
        if (mAdapter != null) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }
        this.mAdapter = adapter;
        if (mAdapter instanceof WrapRecyclerAdapter) {
            mWrapRecyclerAdapter = (WrapRecyclerAdapter) mAdapter;
        } else {
            mWrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
        }
        super.setAdapter(mWrapRecyclerAdapter);

        // 注册一个观察者
        mAdapter.registerAdapterDataObserver(mDataObserver);

        // 解决GridLayout添加头部和底部也要占据一行
        mWrapRecyclerAdapter.adjustSpanSize(this);
    }

    /**
     * 添加头部
     */
    public void addHeaderView(View view) {
        // 如果没有Adapter那么就不添加，也可以选择抛异常提示
        // 让他必须先设置Adapter然后才能添加，这里是仿照ListView的处理方式
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.addHeaderView(view);
        } else {
            throw new NullPointerException("mWrapRecyclerAdapter is null ");
        }
    }

    /**
     * 添加底部
     */
    public void addFooterView(View view) {
        // 如果没有Adapter那么就不添加，也可以选择抛异常提示
        // 让他必须先设置Adapter然后才能添加，这里是仿照ListView的处理方式
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.addFooterView(view);
        } else {
            throw new NullPointerException("mWrapRecyclerAdapter is null ");
        }
    }

    /**
     * 移除顶部
     */
    public void removeHeaderView(View view) {
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.removeHeaderView(view);
        }
    }

    /**
     * 移除底部
     */
    public void removeFooterView(View view) {
        if (mWrapRecyclerAdapter != null) {
            mWrapRecyclerAdapter.removeFooterView(view);
        }
    }

    /**
     * 添加一个空列表数据页面
     */
    public void addEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    /**
     * 添加一个正在加载数据的页面
     */
    public void addLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    /**
     * Adapter数据改变的方法
     */
    private void dataChanged() {
        if (mAdapter.getItemCount() == 0) {
            // 没有数据
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
        }
    }

    //观察着
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            // 观察者 列表Adapter更新 包裹的也需要更新不然列表的notifyDataSetChanged没效果
            if (mWrapRecyclerAdapter != mAdapter) mWrapRecyclerAdapter.notifyDataSetChanged();
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemRangeChanged(positionStart, itemCount);
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            if (mAdapter == null) return;
            // 观察者  列表Adapter更新 包裹的也需要更新不然列表的notifyItemChanged没效果
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemChanged(positionStart, payload);
            dataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemRangeInserted(positionStart, itemCount);
            dataChanged();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mAdapter == null) return;
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemRangeRemoved(positionStart, itemCount);
            dataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mAdapter == null) return;
            if (mWrapRecyclerAdapter != mAdapter)
                mWrapRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
            dataChanged();
        }
    };

}

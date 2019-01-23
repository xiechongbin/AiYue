package com.chexiaoya.aiyue.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chexiaoya.aiyue.RecyclerView.creater.RefreshViewCreator;

/**
 * 可以下拉刷新的RecyclerView
 * Created by xcb on 2019/1/22.
 */
public class RefreshRecyclerView extends WrapRecyclerView {

    /**
     * 下拉刷新辅助类
     */
    private RefreshViewCreator creator;

    /**
     * 手指按下的y坐标
     */
    private int mFingerDownY;

    /**
     * 手指拖拽的阻力指数
     */
    protected float mDragIndex = 0.35f;
    /**
     * 是否正在拖动
     */
    private boolean mCurrentDrag;

    /**
     * 下拉刷新的头部View
     */
    private View mRefreshView;

    /**
     * 下拉刷新头部的高度
     */
    private int mRefreshViewHeight = 0;

    /**
     * 当前的状态
     */
    private int mCurrentRefreshStatus;

    /**
     * 默认状态
     */
    public int REFRESH_STATUS_NORMAL = 1;

    /**
     * 下拉刷新状态
     */
    public int REFRESH_STATUS_PULL_DOWN_REFRESH = 2;

    /**
     * 松开刷新状态
     */
    public int REFRESH_STATUS_LOOSEN_REFRESHING = 3;

    /**
     * 正在刷新状态
     */
    public int REFRESH_STATUS_REFRESHING = 4;

    /**
     * 刷新回调监听
     */
    private OnRefreshListener mListener;


    public RefreshRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 不能直接添加View，需要利用辅助类 提高扩展性能
     */
    public void addRefreshViewCreator(RefreshViewCreator creator) {
        this.creator = creator;
        addRefreshView();
    }

    /**
     * 重写适配器方法
     */
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        addRefreshView();
    }


    /**
     * 触摸事件分发
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置 ,之所以写在dispatchTouchEvent那是因为如果我们处理了条目点击事件，
                // 那么就不会进入onTouchEvent里面，所以只能在这里获取
                mFingerDownY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurrentDrag) {
                    restoreRefreshView();
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 重置刷新的view
     */
    private void restoreRefreshView() {
        int currentTopMargin = ((MarginLayoutParams) mRefreshView.getLayoutParams()).topMargin;
        int finalTopMargin = -mRefreshViewHeight + 1;
        if (mCurrentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            finalTopMargin = 0;
            mCurrentRefreshStatus = REFRESH_STATUS_REFRESHING;
            if (creator != null) {
                creator.onRefreshing();
            }
            if (mListener != null) {
                mListener.onRefresh();
            }
            int distance = currentTopMargin - finalTopMargin;

            // 回弹到指定位置
            ValueAnimator animator = ObjectAnimator.ofFloat(currentTopMargin, finalTopMargin).setDuration(distance);
            final int finalTopMargin1 = finalTopMargin;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currentTopMargin = (float) animation.getAnimatedValue();
                    setRefreshViewMarginTop((int) currentTopMargin);
                    if ((int) (currentTopMargin) == finalTopMargin1 && creator != null) {
                        creator.onPushBack();
                    }
                }

            });
            animator.start();
            mCurrentDrag = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 如果是在最顶部才处理，否则不需要处理
                if (canScrollUp() || mCurrentRefreshStatus == REFRESH_STATUS_REFRESHING) {
                    // 如果没有到达最顶端，也就是说还可以向上滚动就什么都不处理
                    return super.onTouchEvent(e);
                }
                // 解决下拉刷新自动滚动问题
                if (mCurrentDrag) {
                    scrollToPosition(0);
                }
                // 获取手指触摸拖拽的距离
                int distanceY = (int) ((e.getRawY() - mFingerDownY) * mDragIndex);
                // 如果是已经到达头部，并且不断的向下拉，那么不断的改变refreshView的marginTop的值
                if (distanceY > 0) {
                    int marginTop = distanceY - mRefreshViewHeight;
                    setRefreshViewMarginTop(marginTop);
                    updateRefreshStatus(marginTop);
                    mCurrentDrag = true;
                    return false;
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 更新刷新状态
     */
    private void updateRefreshStatus(int marginTop) {
        if (marginTop <= -mRefreshViewHeight) {
            mCurrentRefreshStatus = REFRESH_STATUS_NORMAL;
        } else if (marginTop < 0) {
            mCurrentRefreshStatus = REFRESH_STATUS_PULL_DOWN_REFRESH;
        } else {
            mCurrentRefreshStatus = REFRESH_STATUS_LOOSEN_REFRESHING;
        }
        if (creator != null) {
            creator.onPull(marginTop, mRefreshViewHeight, mCurrentRefreshStatus);
        }
    }

    /**
     * 添加头部刷新view
     */
    public void addRefreshView() {
        Adapter adapter = getAdapter();
        if (adapter != null && creator != null) {
            View view = creator.getRefreshView(getContext(), this);
            if (view != null) {
                addHeaderView(view);
                this.mRefreshView = view;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            if (mRefreshView != null && mRefreshViewHeight <= 0) {
                // 获取头部刷新View的高度
                mRefreshViewHeight = mRefreshView.getMeasuredHeight();
                if (mRefreshViewHeight > 0) {
                    // 隐藏头部刷新的View  marginTop 多留出1px防止无法判断是不是滚动到头部问题
                    setRefreshViewMarginTop(-mRefreshViewHeight + 1);
                }
            }
        }
    }

    /**
     * 设置刷新View的marginTop
     */
    public void setRefreshViewMarginTop(int marginTop) {
        recoverRefreshView();
        MarginLayoutParams params = (MarginLayoutParams) mRefreshView.getLayoutParams();
        if (marginTop < -mRefreshViewHeight + 1) {
            marginTop = -mRefreshViewHeight + 1;
        }
        params.topMargin = marginTop;
        mRefreshView.setLayoutParams(params);
    }

    private void recoverRefreshView() {
        ViewGroup.LayoutParams layoutParams = mRefreshView.getLayoutParams();
        if (layoutParams.height == 0) {
            layoutParams.height = mRefreshViewHeight;
            mRefreshView.setLayoutParams(layoutParams);
        }
    }

    private void hideRefreshView() {
        if (mRefreshView != null) {
            final ViewGroup.LayoutParams layoutParams = mRefreshView.getLayoutParams();
            // 回弹
            ValueAnimator animator = ObjectAnimator.ofFloat(layoutParams.height, 0).setDuration(mRefreshViewHeight);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float height = (float) animation.getAnimatedValue();
                    layoutParams.height = (int) height;
                    mRefreshView.setLayoutParams(layoutParams);
                }

            });
            animator.start();
        }
    }

    /**
     * 判断是不是滚动到了最顶部SwipeRefreshLayout里面copy过来
     */
    public boolean canScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;
        } else {
            return ViewCompat.canScrollVertically(this, -1);
        }
    }

    /**
     * 停止刷新
     */
    public void onStopRefresh() {
        mCurrentRefreshStatus = REFRESH_STATUS_NORMAL;
        restoreRefreshView();
        if (creator != null) {
            creator.onStopRefresh();
        }
        hideRefreshView();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}

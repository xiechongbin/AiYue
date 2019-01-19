package com.chexiaoya.aiyue.view.dataloading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chexiaoya.aiyue.view.dataloading.defaultView.DefaultEmptyView;
import com.chexiaoya.aiyue.view.dataloading.defaultView.DefaultErrorView;
import com.chexiaoya.aiyue.view.dataloading.defaultView.DefaultIngView;
import com.orhanobut.logger.Logger;

/**
 * Created by xcb on 2019/1/19.
 */
public class StateLayout extends FrameLayout {

    private Context mContext;
    private ViewStateInterface mEmptyView;
    private ViewStateInterface mIngView;
    private ViewStateInterface mErrorView;
    private State mState;


    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //initAttributeSet(attrs, defStyleAttr);
        init();
    }

//    private void initAttributeSet(AttributeSet attrs, int defStyleAttr) {
//        TypedArray typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.StateLayout, defStyleAttr, 0);
//        int contentLayoutId = typeArray.getResourceId(R.styleable.StateLayout_state_content_layout, -1);
//        typeArray.recycle();
//        if (contentLayoutId == -1) {
//            throw new NullPointerException("StateSwitchLayout use should set content layout id!!!!!!");
//        }
//       // contentView = LayoutInflater.from(getContext()).inflate(contentLayoutId, this, false);
//    }


    /**
     * 初始化
     */
    private void init() {
        initStateViews();
    }

    /**
     * 初始化各种状态的view
     */
    private void initStateViews() {
        if (mEmptyView == null) {
            mEmptyView = new DefaultEmptyView(mContext);
        }
        if (mErrorView == null) {
            mErrorView = new DefaultErrorView(mContext);
        }
        if (mIngView == null) {
            mIngView = new DefaultIngView(mContext);
        }
        this.addView(mEmptyView.getView());
        this.addView(mErrorView.getView());
        this.addView(mIngView.getView());
        // this.addView(contentView);

    }

    public void setIngStateView(@NonNull ViewStateInterface stateWrap) {
        removeView(mIngView.getView());
        mIngView = stateWrap;
        addView(mIngView.getView());
        if (mState != State.ING) {
            mIngView.getView().setVisibility(GONE);
            mIngView.visible(false);
        } else {
            mIngView.visible(true);
        }
    }

    public void setEmptyStateView(@NonNull ViewStateInterface stateWrap) {
        setEmptyStateView(stateWrap, null);
    }

    public void setEmptyStateView(@NonNull ViewStateInterface stateWrap, OnRetryListener retryListener) {
        removeView(mEmptyView.getView());
        mEmptyView = stateWrap;
        mEmptyView.setOnRetryListener(retryListener);
        addView(mEmptyView.getView());
        if (mState != State.ERROR) {
            mEmptyView.getView().setVisibility(GONE);
            mEmptyView.visible(false);
        } else {
            mEmptyView.visible(true);
        }
    }

    public void setEmptyStateRetryListener(@NonNull OnRetryListener retryListener) {
        mEmptyView.setOnRetryListener(retryListener);
    }

    public void setErrorStateView(@NonNull ViewStateInterface stateWrap) {
        setErrorStateView(stateWrap, null);
    }

    public void setErrorStateView(@NonNull ViewStateInterface stateWrap, OnRetryListener retryListener) {
        removeView(mErrorView.getView());
        mErrorView = stateWrap;
        mErrorView.setOnRetryListener(retryListener);
        addView(mErrorView.getView());
        if (mState != State.ERROR) {
            mErrorView.getView().setVisibility(GONE);
            mErrorView.visible(false);
        } else {
            mErrorView.visible(true);
        }
    }

    public void setErrorStateRetryListener(OnRetryListener retryListener) {
        mErrorView.setOnRetryListener(retryListener);
    }

    /**
     * 设置view的显示状态
     */
    public void switchState(State state) {
        if (mState != null && mState == state) {
            return;
        }
        Logger.e("state>>>" + state);
        switch (state) {
            case ING:
                // contentView.setVisibility(GONE);
                mIngView.getView().setVisibility(VISIBLE);
                mIngView.visible(false);
                mEmptyView.getView().setVisibility(GONE);
                mEmptyView.visible(false);
                mErrorView.getView().setVisibility(GONE);
                mErrorView.visible(false);
                break;
            case EMPTY:
                // contentView.setVisibility(GONE);
                mIngView.getView().setVisibility(GONE);
                mIngView.visible(false);
                mEmptyView.getView().setVisibility(VISIBLE);
                mEmptyView.visible(false);
                mErrorView.getView().setVisibility(GONE);
                mErrorView.visible(false);
                break;
            case ERROR:
                //contentView.setVisibility(GONE);
                mIngView.getView().setVisibility(GONE);
                mIngView.visible(false);
                mEmptyView.getView().setVisibility(GONE);
                mEmptyView.visible(false);
                mErrorView.getView().setVisibility(VISIBLE);
                mErrorView.visible(false);
                break;
            case CONTENT:
                //contentView.setVisibility(VISIBLE);
                mIngView.getView().setVisibility(GONE);
                mIngView.visible(false);
                mEmptyView.getView().setVisibility(GONE);
                mEmptyView.visible(false);
                mErrorView.getView().setVisibility(GONE);
                mErrorView.visible(false);
        }
        mState = state;
    }

}

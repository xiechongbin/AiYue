package com.chexiaoya.aiyue.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chexiaoya.aiyue.interfaces.OnTabClickListener;

/**
 * 自定义底部导航view
 * Created by xcb on 2018/12/27.
 */
public class BottomBar extends LinearLayout implements View.OnClickListener {

    private LinearLayout tabContainer;

    private LayoutParams tabLayoutParams;
    private LayoutParams tabContainerParams;
    private Context mContext;
    private OnTabClickListener listener;
    private int currentPosition = 0;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    /**
     * 初始化容器
     */
    private void initView() {
        this.setOrientation(HORIZONTAL);


        tabContainer = new LinearLayout(mContext);
        tabContainer.setOrientation(HORIZONTAL);
        tabContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        tabLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        tabLayoutParams.weight = 1;

        this.addView(tabContainer, tabContainerParams);

    }

    /**
     * 添加底部导航子控件
     */
    public BottomBar addTab(BottomBarTabView bottomBarTabView) {
        bottomBarTabView.setOnClickListener(this);
        tabContainer.addView(bottomBarTabView, tabLayoutParams);
        bottomBarTabView.setPosition(tabContainer.getChildCount());
        return this;
    }

    public void addOnTabClickListener(OnTabClickListener listener) {
        this.listener = listener;
    }

    public void setAnimation(boolean doAnimation) {
        if (doAnimation) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 160);
            animator.setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    tabContainerParams.height = (int) value;
                    tabContainer.setLayoutParams(tabContainerParams);
                }
            });
            animator.start();
        } else {
            tabContainerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tabContainer.setLayoutParams(tabContainerParams);
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            if (v instanceof BottomBarTabView) {
                BottomBarTabView bottomBarTabView = (BottomBarTabView) v;
                listener.onTabClick(bottomBarTabView);
                int pos = bottomBarTabView.getPosition();
                int count = tabContainer.getChildCount();
                for (int i = 0; i < count; i++) {
                    BottomBarTabView view = (BottomBarTabView) tabContainer.getChildAt(i);
                    if (view != null) {
                        Log.d("tag", "按下 = " + pos + ">>>pos = " + view.getPosition());
                        view.setSelected(view.getPosition() == pos);
                    } else {
                        Log.d("tag", "kong");
                    }
                }
            }
        }
    }
}

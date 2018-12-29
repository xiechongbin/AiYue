package com.chexiaoya.aiyue.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.utils.AndroidDeviceUtils;

/**
 * 底部导航单个icon
 * Created by xcb on 2018/12/27.
 */
public class BottomBarTabView extends LinearLayout {
    private ImageView iv_icon;
    private TextView tv_title;
    private Context mContext;
    private int position;

    public BottomBarTabView(Context context, @DrawableRes int id, String title) {
        this(context, null, id, title);
    }

    public BottomBarTabView(Context context, AttributeSet attrs, @DrawableRes int id, String title) {
        this(context, attrs, 0, id, title);
    }

    public BottomBarTabView(Context context, AttributeSet attrs, int defStyleAttr, @DrawableRes int id, String title) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(id, title);
    }

    /**
     * 初始化
     */
    private void initView(int icon_id, String title) {
        this.setOrientation(HORIZONTAL);//设置为垂直方向
        this.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_bottom_bar_bg));
        iv_icon = new ImageView(mContext);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics());
        LayoutParams layoutParams = new LayoutParams(size, size);
        layoutParams.topMargin = AndroidDeviceUtils.dip2px(mContext, 3.0f);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        iv_icon.setLayoutParams(layoutParams);
        iv_icon.setScaleType(ImageView.ScaleType.CENTER);
        iv_icon.setImageResource(icon_id);

        tv_title = new TextView(mContext);
        tv_title.setText(title);

        LayoutParams layoutParams_textView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams_textView.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams_textView.topMargin = AndroidDeviceUtils.dip2px(mContext, 2.8f);
        layoutParams_textView.bottomMargin = AndroidDeviceUtils.dip2px(mContext, 2.8f);

        tv_title.setLayoutParams(layoutParams_textView);
        tv_title.setTextSize(AndroidDeviceUtils.dip2px(mContext, 2.8f));

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(VERTICAL);

        linearLayout.addView(iv_icon, 0);
        linearLayout.addView(tv_title, 1);

        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.CENTER_VERTICAL;

        this.addView(linearLayout, layoutParams1);
    }

    public BottomBarTabView setViewId(@IdRes int id) {
        this.setId(id);
        return this;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            iv_icon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
            tv_title.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            iv_icon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unSelect));
            tv_title.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unSelect));
        }
    }

    /**
     * 设置控件处于第几个位置
     */
    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}

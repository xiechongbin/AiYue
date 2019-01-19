package com.chexiaoya.aiyue.view.dataloading.defaultView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.view.dataloading.OnRetryListener;
import com.chexiaoya.aiyue.view.dataloading.ViewStateInterface;

/**
 * 默认的空视图
 * Created by xcb on 2019/1/19.
 */
public class DefaultIngView extends FrameLayout implements ViewStateInterface {
    private Context mContext;

    public DefaultIngView(Context context) {
        this(context, null);
    }

    public DefaultIngView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DefaultIngView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_default_ing, this, false);
        this.addView(view);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setPromptMessage(String msg) {

    }

    @Override
    public void setOnRetryListener(OnRetryListener listener) {

    }

    @Override
    public void visible(boolean visible) {

    }
}

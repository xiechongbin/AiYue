package com.chexiaoya.aiyue.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.utils.AndroidDeviceUtils;
import com.chexiaoya.aiyue.utils.Constant;
import com.chexiaoya.aiyue.utils.GlideUtils;
import com.chexiaoya.aiyue.utils.LaunchUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.fl_ignore_ad)
    public FrameLayout fl_ignore_ad;
    @BindView(R.id.tv_skip)
    public TextView tv_skip;
    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;
    private CountDownTimer countDownTimer;
    @BindString(R.string.ignore_ad)
    public String close_ad;

    @Override
    public int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initIgnoreIconHeight();
    }

    @Override
    public void initData() {
        GlideUtils.loadImage(this, Constant.FIRST_PAGE_PIC_URL, ivAd);
        countDown();
        initGif();
    }

    @OnClick(R.id.fl_ignore_ad)
    public void onIgnoreADClick(View view) {
        jumpToMain();
    }


    /**
     * 设置高度 防止盖住状态栏
     */
    private void initIgnoreIconHeight() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fl_ignore_ad.getLayoutParams();
        int marginTop = layoutParams.topMargin;
        int statusBarHeight = AndroidDeviceUtils.getStatusBarHeight(this);
        layoutParams.topMargin = marginTop + statusBarHeight;
        fl_ignore_ad.setLayoutParams(layoutParams);
    }

    /**
     * 初始化gif动画
     */
    private void initGif() {
        final GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
        gifImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gifDrawable != null) {
                    gifDrawable.start();
                }
            }
        }, 500);
    }

    /**
     * 倒计时
     */
    private void countDown() {
        countDownTimer = new CountDownTimer(Constant.AD_PAGE_TOTAL_TIME, Constant.AD_PAGE_COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int time = (int) (millisUntilFinished / 1000);
                String str = close_ad + time;
                tv_skip.setText(str);
            }

            @Override
            public void onFinish() {
                jumpToMain();
            }
        };
        countDownTimer.start();
    }

    /**
     * 跳转到主页面
     */
    public void jumpToMain() {
        LaunchUtils.launchMainActivity(this);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gifImageView != null) {
            gifImageView.clearAnimation();
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}

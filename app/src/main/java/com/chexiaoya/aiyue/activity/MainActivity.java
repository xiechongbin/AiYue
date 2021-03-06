package com.chexiaoya.aiyue.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.fragment.BaseFragment;
import com.chexiaoya.aiyue.fragment.JianDanFragment;
import com.chexiaoya.aiyue.fragment.MyInfoFragment;
import com.chexiaoya.aiyue.fragment.NewsFragment;
import com.chexiaoya.aiyue.fragment.VideoFragment;
import com.chexiaoya.aiyue.interfaces.BackHandledInterface;
import com.chexiaoya.aiyue.interfaces.OnTabClickListener;
import com.chexiaoya.aiyue.view.BottomBar;
import com.chexiaoya.aiyue.view.BottomBarTabView;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabClickListener, BackHandledInterface {

    @BindView(R.id.bottomBar)
    public BottomBar bottomBar;

    @BindString(R.string.news)
    public String news;
    @BindString(R.string.video)
    public String video;
    @BindString(R.string.jian_dan)
    public String jian_dan;
    @BindString(R.string.my)
    public String my;
    private JianDanFragment jianDanFragment;
    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private MyInfoFragment myInfoFragment;

    private BaseFragment mBackHandedFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBottomBar();
        addCustomActionBar();
    }

    @Override
    public void initData() {
        initFragment();
    }

    @Override
    public void onTabClick(View view) {
        switch (view.getId()) {
            case R.id.tab_first:
                if (newsFragment != null && !newsFragment.isVisible()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.show(newsFragment);
                    fragmentTransaction.hide(videoFragment);
                    fragmentTransaction.hide(jianDanFragment);
                    fragmentTransaction.hide(myInfoFragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tab_second:
                if (videoFragment != null && !videoFragment.isVisible()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.show(videoFragment);
                    fragmentTransaction.hide(newsFragment);
                    fragmentTransaction.hide(jianDanFragment);
                    fragmentTransaction.hide(myInfoFragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tab_third:
                if (jianDanFragment != null && !jianDanFragment.isVisible()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.show(jianDanFragment);
                    fragmentTransaction.hide(videoFragment);
                    fragmentTransaction.hide(newsFragment);
                    fragmentTransaction.hide(myInfoFragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;
            case R.id.tab_fourth:
                if (myInfoFragment != null && !myInfoFragment.isVisible()) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.show(myInfoFragment);
                    fragmentTransaction.hide(videoFragment);
                    fragmentTransaction.hide(jianDanFragment);
                    fragmentTransaction.hide(newsFragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;
        }
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        jianDanFragment = JianDanFragment.newInstance();
        newsFragment = NewsFragment.newInstance();
        videoFragment = VideoFragment.newInstance();
        myInfoFragment = MyInfoFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, newsFragment, NewsFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.container, videoFragment, VideoFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.container, jianDanFragment, JianDanFragment.class.getSimpleName());
        fragmentTransaction.add(R.id.container, myInfoFragment, MyInfoFragment.class.getSimpleName());

        if (!newsFragment.isVisible()) {
            fragmentTransaction.show(newsFragment);
        }
        fragmentTransaction.hide(videoFragment);
        fragmentTransaction.hide(myInfoFragment);
        fragmentTransaction.hide(jianDanFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    /**
     * 初始化底部导航栏
     */
    private void initBottomBar() {
        bottomBar.addTab(new BottomBarTabView(this, R.drawable.ic_news, news).setViewId(R.id.tab_first))
                .addTab(new BottomBarTabView(this, R.drawable.ic_video, video).setViewId(R.id.tab_second))
                .addTab(new BottomBarTabView(this, R.drawable.ic_jiandan, jian_dan).setViewId(R.id.tab_third))
                .addTab(new BottomBarTabView(this, R.drawable.ic_my, my).setViewId(R.id.tab_fourth));
        bottomBar.setAnimation(true);
        bottomBar.addOnTabClickListener(this);
    }

    /**
     * 自定义标题栏
     */
    public void addCustomActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && !actionBar.isShowing()) {
            //actionBar.show();//隐藏标题栏
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }


}

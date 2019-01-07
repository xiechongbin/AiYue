package com.chexiaoya.aiyue.activity;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Toast;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.interfaces.OnTabClickListener;
import com.chexiaoya.aiyue.view.BottomBar;
import com.chexiaoya.aiyue.view.BottomBarTabView;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabClickListener {

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

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        initBottomBar();
        addCustomActionBar();
    }

    @Override
    public void onTabClick(View view) {
        Toast.makeText(this, view.getId() + "", Toast.LENGTH_SHORT).show();
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
            actionBar.show();//隐藏标题栏
        }
    }
}

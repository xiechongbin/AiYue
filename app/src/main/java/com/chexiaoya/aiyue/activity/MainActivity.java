package com.chexiaoya.aiyue.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.fragment.BaseFragment;
import com.chexiaoya.aiyue.fragment.ChannelManagerFragment;
import com.chexiaoya.aiyue.interfaces.BackHandledInterface;
import com.chexiaoya.aiyue.interfaces.OnTabClickListener;
import com.chexiaoya.aiyue.utils.Constant;
import com.chexiaoya.aiyue.view.BottomBar;
import com.chexiaoya.aiyue.view.BottomBarTabView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    private BaseFragment mBackHandedFragment;

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
        showChannelSelectFragment();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNews();
            }
        }).start();

    }

    private void showChannelSelectFragment() {
        ChannelManagerFragment channelManagerFragment = ChannelManagerFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_page_up, R.anim.anim_page_dowm);
        transaction.add(R.id.container, channelManagerFragment, ChannelManagerFragment.class.getSimpleName());
        transaction.commitAllowingStateLoss();
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

    private void getNews() {
        try {
            //http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
            URL url = new URL(Constant.URL + "?" + "type=top&key=" + Constant.APP_KEY);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                buffer.append(temp);
            }
            bufferedReader.close();//记得关闭
            reader.close();
            inputStream.close();
            Log.e("MAIN", buffer.toString());//打印结果

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

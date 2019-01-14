package com.chexiaoya.aiyue.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.NewsInfoBean;
import com.chexiaoya.aiyue.fragment.BaseFragment;
import com.chexiaoya.aiyue.fragment.JianDanFragment;
import com.chexiaoya.aiyue.fragment.MyInfoFragment;
import com.chexiaoya.aiyue.fragment.NewsFragment;
import com.chexiaoya.aiyue.fragment.VideoFragment;
import com.chexiaoya.aiyue.interfaces.BackHandledInterface;
import com.chexiaoya.aiyue.interfaces.OnTabClickListener;
import com.chexiaoya.aiyue.interfaces.RetrofitRequestInterface;
import com.chexiaoya.aiyue.utils.Constant;
import com.chexiaoya.aiyue.view.BottomBar;
import com.chexiaoya.aiyue.view.BottomBarTabView;

import butterknife.BindString;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    public void initData() {
        initBottomBar();
        addCustomActionBar();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                //request();
            }
        }).start();

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

    private void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL + "?" + "type=top")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //步骤5:创建 网络请求接口 的实例
        RetrofitRequestInterface retrofitRequestInterface = retrofit.create(RetrofitRequestInterface.class);
        //对 发送请求 进行封装
        Call<NewsInfoBean> infoBeanCall = retrofitRequestInterface.getNews();
        //对 发送请求 进行封装
        infoBeanCall.enqueue(new Callback<NewsInfoBean>() {
            @Override
            public void onResponse(Call<NewsInfoBean> call, Response<NewsInfoBean> response) {
                NewsInfoBean bean = response.body();
                Log.e("MAIN", bean.toString());//打印结果
            }
            
            @Override
            public void onFailure(Call<NewsInfoBean> call, Throwable t) {

            }
        });
    }
}

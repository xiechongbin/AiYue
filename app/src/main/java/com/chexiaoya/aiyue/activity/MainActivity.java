package com.chexiaoya.aiyue.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.NewsInfoBean;
import com.chexiaoya.aiyue.fragment.BaseFragment;
import com.chexiaoya.aiyue.fragment.ChannelManagerFragment;
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
               // getNews();
                request();
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

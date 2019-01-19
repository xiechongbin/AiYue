package com.chexiaoya.aiyue.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.adapter.NewsAdapter;
import com.chexiaoya.aiyue.adapter.divider.RecycleViewDivider;
import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.bean.NewsDataBean;
import com.chexiaoya.aiyue.bean.NewsRequestJsonBean;
import com.chexiaoya.aiyue.interfaces.OnNewsItemClickListener;
import com.chexiaoya.aiyue.interfaces.RetrofitRequestInterface;
import com.chexiaoya.aiyue.utils.AndroidDeviceUtils;
import com.chexiaoya.aiyue.utils.Constant;
import com.chexiaoya.aiyue.view.dataloading.State;
import com.chexiaoya.aiyue.view.dataloading.StateLayout;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 详情页面
 * Created by xcb on 2019/1/16.
 */
public class DetailsFragment extends BaseFragment implements OnNewsItemClickListener {

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    Unbinder unbinder;
    @BindView(R.id.state_layout)
    StateLayout state_layout;
    private NewsAdapter adapter;
    private Channel channel;

    public static DetailsFragment newInstance(Channel channel) {
        Bundle args = new Bundle();
        args.putSerializable("channel", channel);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_details;
    }

    @Override
    public void initView(Object obj) {
        View rootView = (View) obj;
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle args = getArguments();
        if (args != null) {
            channel = (Channel) args.getSerializable("channel");
        }
        rvNews.setLayoutManager(new LinearLayoutManager(activity));
        RecycleViewDivider divider = new RecycleViewDivider(activity,
                LinearLayoutManager.HORIZONTAL,
                AndroidDeviceUtils.dip2px(activity, 30),
                getResources().getDrawable(R.drawable.layer_recyclew_divider));
        rvNews.addItemDecoration(divider);
        adapter = new NewsAdapter(activity, this);
        rvNews.setAdapter(adapter);
        state_layout.switchState(State.ING);
        request(channel);

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void request(Channel channel) {
        if (channel == null) {
            return;
        }
        Logger.e("channelid == " + channel.getChannelId());
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL + "?" + "type=" + channel.getChannelId())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //步骤5:创建 网络请求接口 的实例
        RetrofitRequestInterface retrofitRequestInterface = retrofit.create(RetrofitRequestInterface.class);
        //对 发送请求 进行封装
        Call<NewsRequestJsonBean> infoBeanCall = retrofitRequestInterface.getNews();
        //对 发送请求 进行封装
        infoBeanCall.enqueue(new Callback<NewsRequestJsonBean>() {
            @Override
            public void onResponse(Call<NewsRequestJsonBean> call, Response<NewsRequestJsonBean> response) {
                NewsRequestJsonBean newsRequestJsonBean = response.body();
                if (newsRequestJsonBean != null) {
                    if (newsRequestJsonBean.getError_code() == 0) {
                        if (newsRequestJsonBean.getResult().getData() != null && newsRequestJsonBean.getResult().getData().size() > 0) {
                            state_layout.setVisibility(View.GONE);
                            rvNews.setVisibility(View.VISIBLE);
                            adapter.setData(response.body());
                            adapter.notifyDataSetChanged();
                        } else {
                            state_layout.setVisibility(View.VISIBLE);
                            state_layout.switchState(State.EMPTY);
                        }
                    } else {
                        state_layout.setVisibility(View.VISIBLE);
                        state_layout.switchState(State.ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsRequestJsonBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNewsItemClick(NewsDataBean newsDataBean) {
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            NewsDetailsFragment detailsFragment = NewsDetailsFragment.newInstance(newsDataBean);
            transaction.replace(R.id.fl_news_details, detailsFragment, NewsDetailsFragment.class.getSimpleName());
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

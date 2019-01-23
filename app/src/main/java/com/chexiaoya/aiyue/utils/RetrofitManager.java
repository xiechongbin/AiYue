package com.chexiaoya.aiyue.utils;

import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.bean.NewsRequestJsonBean;
import com.chexiaoya.aiyue.interfaces.INetWorkCallback;
import com.chexiaoya.aiyue.interfaces.RetrofitRequestInterface;
import com.orhanobut.logger.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 * Created by xcb on 2019/1/23.
 */
public class RetrofitManager {

    private static RetrofitManager instance;

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public void requestNews(Channel channel, final INetWorkCallback<NewsRequestJsonBean> callback) {
        if (channel == null) {
            return;
        }
        Logger.e("channelId == " + channel.getChannelId());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建 网络请求接口 的实例
        RetrofitRequestInterface retrofitRequestInterface = retrofit.create(RetrofitRequestInterface.class);
        //对 发送请求 进行封装
        Call<NewsRequestJsonBean> infoBeanCall = retrofitRequestInterface.getNews(channel.getChannelId(), Constant.APP_KEY);
        //对 发送请求 进行封装
        infoBeanCall.enqueue(new Callback<NewsRequestJsonBean>() {
            @Override
            public void onResponse(Call<NewsRequestJsonBean> call, Response<NewsRequestJsonBean> response) {
                if (callback != null) {
                    callback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsRequestJsonBean> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure();
                }
            }
        });
    }

}

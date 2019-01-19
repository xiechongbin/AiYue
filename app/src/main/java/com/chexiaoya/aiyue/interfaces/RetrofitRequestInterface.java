package com.chexiaoya.aiyue.interfaces;

import com.chexiaoya.aiyue.bean.NewsRequestJsonBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * 网络请求的接口
 * Created by xcb on 2019/1/12.
 */
public interface RetrofitRequestInterface {

    @GET("index?type=top&key=467ada4c0c147f7439dbf2238affe240")
    Call<NewsRequestJsonBean> getNews();
}

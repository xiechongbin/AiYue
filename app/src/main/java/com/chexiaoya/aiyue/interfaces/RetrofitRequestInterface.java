package com.chexiaoya.aiyue.interfaces;

import com.chexiaoya.aiyue.bean.NewsRequestJsonBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 网络请求的接口
 * Created by xcb on 2019/1/12.
 */
public interface RetrofitRequestInterface {

    //http://v.juhe.cn/toutiao/
    //http://v.juhe.cn/toutiao/index?type=top&key=APPKEY
    @GET("index")
    Call<NewsRequestJsonBean> getNews(@Query("type") String type, @Query("key") String key);

}

package com.chexiaoya.aiyue.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * Glide图片加载工具类
 * Created by xcb on 2018/12/27.
 */
public class GlideUtils {

    /**
     * 加载图片
     */
    public static void loadImage(Context context, String url, ImageView target) {
        Glide.with(context).
                load(url).
                apply(new RequestOptions().
                        centerCrop().
                        diskCacheStrategy(DiskCacheStrategy.ALL)).
                transition(new DrawableTransitionOptions().crossFade(800)).
                into(target);
    }
}

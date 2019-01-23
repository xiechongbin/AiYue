package com.chexiaoya.aiyue.RecyclerView.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用的ViewHolder
 * Created by xcb on 2019/1/21.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    // 用来存放view 减少findViewById的次数
    private SparseArray<View> mViews;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 设置文本
     */
    public RecyclerViewHolder setText(int viewId, CharSequence charSequence) {
        TextView tv = getView(viewId);
        tv.setText(charSequence);
        return this;
    }

    /**
     * 设置图片
     */
    public RecyclerViewHolder setImage(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置图片
     */
    public RecyclerViewHolder setImage(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 通过第三方框架加载网络图片
     */
    public RecyclerViewHolder setImageByUrl(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getView(viewId);
        if (imageLoader == null) {
            throw new NullPointerException("imageLoader is null");
        }
        imageLoader.displayImage(imageView.getContext(), imageView, imageLoader.getImagePath());
        return this;
    }


    /**
     * 设置View的Visibility
     */
    public RecyclerViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置item的点击事件
     */
    public RecyclerViewHolder setOnItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置item的长按事件
     */
    public RecyclerViewHolder setOnItemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 设置view的点击事件
     */
    public RecyclerViewHolder setOnViewClickListener(int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }


    /**
     * 获取view
     */
    private <T extends View> T getView(int viewId) {
        //先从SparseArray中查找
        View view = null;
        if (mViews != null && mViews.size() > 0) {
            view = mViews.get(viewId);
        }
        // 直接从ItemView中找
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 图片加载，这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
     * 也可以不写这个类
     */
    public abstract static class HolderImageLoader {
        private String mImagePath;


        protected HolderImageLoader(String mImagePath) {
            this.mImagePath = mImagePath;
        }

        public String getImagePath() {
            return mImagePath;
        }

        public abstract void displayImage(Context context, ImageView imageView, String mImagePath);
    }


}

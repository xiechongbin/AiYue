package com.chexiaoya.aiyue.RecyclerView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chexiaoya.aiyue.RecyclerView.interfaces.MultiTypeSupport;
import com.chexiaoya.aiyue.RecyclerView.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 通用适配器
 * Created by xcb on 2019/1/21.
 */
public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected LayoutInflater layoutInflater;
    protected Context mContext;
    private List<T> dataList;
    private int layoutId;

    //多布局支持
    private MultiTypeSupport multiTypeSupport;

    public CommonRecyclerViewAdapter(Context context, List<T> dataList, int layoutId) {
        this.dataList = dataList;
        this.layoutId = layoutId;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 多布局支持
     */
    public CommonRecyclerViewAdapter(Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.multiTypeSupport = multiTypeSupport;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // 多布局支持
        if (multiTypeSupport != null) {
            layoutId = viewType;
        }
        // 先inflate数据
        View itemView = layoutInflater.inflate(layoutId, viewGroup, false);
        // 返回ViewHolder
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i >= 0 && i < dataList.size()) {
            convert(viewHolder, dataList.get(i));
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (multiTypeSupport != null) {
            return multiTypeSupport.getLayoutId(dataList.get(position), position);
        } else {
            return super.getItemViewType(position);
        }
    }

    public abstract void convert(ViewHolder holder, T item);
}

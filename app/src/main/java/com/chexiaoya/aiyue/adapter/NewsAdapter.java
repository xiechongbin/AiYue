package com.chexiaoya.aiyue.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.NewsDataBean;
import com.chexiaoya.aiyue.bean.NewsRequestJsonBean;
import com.chexiaoya.aiyue.bean.NewsResultBean;
import com.chexiaoya.aiyue.interfaces.OnNewsItemClickListener;
import com.chexiaoya.aiyue.utils.GlideUtils;

import java.util.List;

/**
 * Created by xcb on 2019/1/18.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private NewsRequestJsonBean newsRequestJsonBeanList;
    private List<NewsDataBean> newsDataBeanList;
    private Context context;
    private OnNewsItemClickListener clickListener;

    public NewsAdapter(Context context, OnNewsItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_news_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof NewsViewHolder) {
            NewsViewHolder holder = (NewsViewHolder) viewHolder;
            final NewsDataBean dataBean = newsDataBeanList.get(position);
            if (dataBean != null) {
                holder.tv_news_title.setText(dataBean.getTitle());
                holder.tv_news_date.setText(dataBean.getDate());
                holder.tv_news_author_name.setText(dataBean.getAuthor_name());
                GlideUtils.loadImage(context, dataBean.getThumbnail_pic_s(), holder.iv_news_thumb1);
                GlideUtils.loadImage(context, dataBean.getThumbnail_pic_s02(), holder.iv_news_thumb2);
                GlideUtils.loadImage(context, dataBean.getThumbnail_pic_s03(), holder.iv_news_thumb3);
            }
            final int pos = viewHolder.getAdapterPosition();
            holder.iv_news_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsDataBeanList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, newsDataBeanList.size());

                }
            });
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onNewsItemClick(dataBean);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return newsDataBeanList == null ? 0 : newsDataBeanList.size();
    }


    public void setData(NewsRequestJsonBean newsRequestJsonBeans) {
        this.newsRequestJsonBeanList = newsRequestJsonBeans;
        if (newsRequestJsonBeanList != null) {
            NewsResultBean resultBean = newsRequestJsonBeans.getResult();
            if (resultBean != null) {
                newsDataBeanList = resultBean.getData();
            }
        }

    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_news_thumb1;
        private ImageView iv_news_thumb2;
        private ImageView iv_news_thumb3;
        private ImageView iv_news_delete;
        private TextView tv_news_title;
        private TextView tv_news_date;
        private TextView tv_news_author_name;
        private View rootView;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            iv_news_thumb1 = itemView.findViewById(R.id.iv_news_thumb1);
            iv_news_thumb2 = itemView.findViewById(R.id.iv_news_thumb2);
            iv_news_thumb3 = itemView.findViewById(R.id.iv_news_thumb3);
            iv_news_delete = itemView.findViewById(R.id.iv_news_delete);
            tv_news_title = itemView.findViewById(R.id.tv_news_title);
            tv_news_date = itemView.findViewById(R.id.tv_news_date);
            tv_news_author_name = itemView.findViewById(R.id.tv_news_author_name);
        }
    }
}

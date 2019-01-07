package com.chexiaoya.aiyue.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.Channel;

import java.util.List;

/**
 * 频道管理适配器
 * Created by xcb on 2019/1/7.
 */
public class ChannelManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Channel> channelList;
    private Context context;

    public ChannelManagerAdapter(List<Channel> channelList, Context context) {
        this.channelList = channelList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Channel.TYPE_MY_CHANNEL_TITLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_channel_item_type1, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == Channel.TYPE_MY_CHANNEL) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_channel_item, parent, false);
            return new ViewHolder3(view);
        } else if (viewType == Channel.TYPE_ADD_CHANNEL_TITLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_channel_item_type2, parent, false);
            return new ViewHolder2(view);
        } else if (viewType == Channel.TYPE_ADD_CHANNEL) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_channel_item_type3, parent, false);
            return new ViewHolder4(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {
            final ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context.getResources().getString(R.string.edit).equals(holder1.tv_edit.getText().toString())) {
                        holder1.tv_edit.setText(R.string.finish);
                    } else if (context.getResources().getString(R.string.finish).equals(holder1.tv_edit.getText().toString())) {
                        holder1.tv_edit.setText(R.string.edit);
                    }
                }
            });
        } else if (holder instanceof ViewHolder2) {
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.tv_recommend.setText(R.string.add_channel);
        } else if (holder instanceof ViewHolder3) {
            ViewHolder3 holder3 = (ViewHolder3) holder;
            holder3.iv_delete_item.setVisibility(View.GONE);
            holder3.tv_my_channel.setText(channelList.get(position).getChannelName());
            //长按事件
            holder3.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        } else if (holder instanceof ViewHolder4) {
            ViewHolder4 holder4 = (ViewHolder4) holder;
            holder4.tv_recommend_channel.setText(channelList.get(position).getChannelName());
            holder4.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (channelList != null && channelList.size() > 0) {
            Channel channel = channelList.get(position);
            if (channel != null) {
                return channel.getItemType();
            }
        }
        return 0;
    }


    /**
     * 我的频道 title
     */
    class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView tv_edit;

        public ViewHolder1(View itemView) {
            super(itemView);
            tv_edit = itemView.findViewById(R.id.tv_edit);
        }
    }

    /**
     * 添加频道 title
     */
    class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView tv_recommend;

        public ViewHolder2(View itemView) {
            super(itemView);
            tv_recommend = itemView.findViewById(R.id.tv_recommend);
        }
    }

    /**
     * 我的频道item
     */
    class ViewHolder3 extends RecyclerView.ViewHolder {
        private ImageView iv_delete_item;
        private TextView tv_my_channel;
        private View view;

        public ViewHolder3(View itemView) {
            super(itemView);
            iv_delete_item = itemView.findViewById(R.id.iv_delete_item);
            tv_my_channel = itemView.findViewById(R.id.tv_my_channel);
            view = itemView;
        }
    }

    /**
     * 推荐频道item
     */
    class ViewHolder4 extends RecyclerView.ViewHolder {
        private TextView tv_recommend_channel;
        private View view;

        public ViewHolder4(View itemView) {
            super(itemView);
            tv_recommend_channel = itemView.findViewById(R.id.tv_recommend_channel);
            view = itemView;
        }
    }

}
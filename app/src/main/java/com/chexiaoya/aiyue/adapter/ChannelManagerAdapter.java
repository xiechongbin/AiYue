package com.chexiaoya.aiyue.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chexiaoya.aiyue.R;
import com.chexiaoya.aiyue.bean.Channel;
import com.chexiaoya.aiyue.interfaces.OnChannelChangeListener;

import java.util.List;

/**
 * 频道管理适配器
 * Created by xcb on 2019/1/7.
 */
public class ChannelManagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ROW_COUNT = 4;
    private List<Channel> channelList;
    private Context context;
    private boolean showSelectedIcon;
    private int lastPosition;
    private OnChannelChangeListener listener;


    public ChannelManagerAdapter(List<Channel> channelList, Context context, OnChannelChangeListener listener) {
        this.channelList = channelList;
        this.context = context;
        lastPosition = getLastMyChannelItem();
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Channel.TYPE_MY_CHANNEL_TITLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_channel_item_type1, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == Channel.TYPE_MY_CHANNEL) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_channel_item, parent, false);
            view.setElevation(11);
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
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
                    showSelectedIcon = !showSelectedIcon;
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof ViewHolder2) {
            ViewHolder2 holder2 = (ViewHolder2) holder;
            holder2.tv_recommend.setText(R.string.add_channel);
        } else if (holder instanceof ViewHolder3) {
            ViewHolder3 holder3 = (ViewHolder3) holder;
            Channel channel = channelList.get(holder.getAdapterPosition());
            holder3.tv_my_channel.setText(channelList.get(position).getChannelName());
            holder3.iv_delete_item.setVisibility(showSelectedIcon ? View.VISIBLE : View.GONE);
            if (channel != null && channel.getChannelType() == 1) {
                holder3.iv_delete_item.setVisibility(View.GONE);
            }
            holder3.iv_delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Channel channel = channelList.get(holder.getAdapterPosition());

                    Channel add = new Channel();
                    add.setChannelId(channel.getChannelId());
                    add.setChannelName(channel.getChannelName());
                    add.setItemType(Channel.TYPE_ADD_CHANNEL);
                    add.setChannelType(channel.getChannelType());
                    add.setChannelSelect(channel.isChannelSelect());
                    add.setChannelAdd(false);
                    add.saveOrUpdate("channelName = ?", add.getChannelName());

                    channelList.add(channelList.size() - 1, add);

                    channelList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    lastPosition--;

                    notifyItemInserted(channelList.size() - 1);
                    notifyItemRangeChanged(holder.getAdapterPosition(), channelList.size());
                    if (listener != null) {
                        listener.onRemoveChannel(add);
                    }

                }
            });
            //长按事件
            holder3.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showSelectedIcon = !showSelectedIcon;
                    notifyDataSetChanged();
                    return true;
                }
            });
        } else if (holder instanceof ViewHolder4) {
            ViewHolder4 holder4 = (ViewHolder4) holder;
            holder4.tv_recommend_channel.setText("+ " + channelList.get(position).getChannelName());
            holder4.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Channel channel = channelList.get(holder.getAdapterPosition());
                    if (channel != null) {
                        channel.setItemType(Channel.TYPE_MY_CHANNEL);
                        Channel add = new Channel();
                        add.setChannelId(channel.getChannelId());
                        add.setChannelName(channel.getChannelName());
                        add.setItemType(Channel.TYPE_MY_CHANNEL);
                        add.setChannelType(channel.getChannelType());
                        add.setChannelSelect(channel.isChannelSelect());
                        add.setChannelAdd(true);
                        add.saveOrUpdate("channelName = ?", add.getChannelName());
                        channelList.remove(holder.getAdapterPosition());
                        channelList.add(lastPosition + 1, add);

                        if (listener != null) {
                            listener.onAddChannel(add);
                        }
                    }


                    //插入
                    notifyItemInserted(lastPosition + 1);
                    lastPosition++;

                    //移除
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), channelList.size());

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
     * 获取我的频道的最后一个position
     */
    private int getLastMyChannelItem() {
        int position = 0;
        for (Channel channel : channelList) {
            if (channel.getItemType() == Channel.TYPE_MY_CHANNEL) {
                position++;
            }
        }
        return position;
    }

    /**
     * 网格布局中的某个item设置为独占一行
     */
    public GridLayoutManager setSpanCount(final GridLayoutManager gridLayoutManager) {
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int pos) {
                int type = getItemViewType(pos);
                if (type == Channel.TYPE_ADD_CHANNEL_TITLE || type == Channel.TYPE_MY_CHANNEL_TITLE) {
                    return 4;
                } else {
                    return 1;
                }
            }
        });
        return gridLayoutManager;
    }

    /**
     * 我的频道 title
     */
    class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView tv_edit;

        ViewHolder1(View itemView) {
            super(itemView);
            tv_edit = itemView.findViewById(R.id.tv_edit);
        }
    }

    /**
     * 添加频道 title
     */
    class ViewHolder2 extends RecyclerView.ViewHolder {
        private TextView tv_recommend;

        ViewHolder2(View itemView) {
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

        ViewHolder3(View itemView) {
            super(itemView);
            iv_delete_item = itemView.findViewById(R.id.iv_delete_item);
            tv_my_channel = itemView.findViewById(R.id.tv_channel);
            view = itemView;
        }
    }

    /**
     * 推荐频道item
     */
    class ViewHolder4 extends RecyclerView.ViewHolder {
        private TextView tv_recommend_channel;
        private View view;

        ViewHolder4(View itemView) {
            super(itemView);
            tv_recommend_channel = itemView.findViewById(R.id.tv_recommend_channel);
            view = itemView;
        }
    }

}

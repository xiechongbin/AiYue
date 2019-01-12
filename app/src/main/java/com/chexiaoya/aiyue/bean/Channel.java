package com.chexiaoya.aiyue.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by xcb on 2019/1/7.
 */
public class Channel extends LitePalSupport {
    public static final int TYPE_MY_CHANNEL_TITLE = 1;
    public static final int TYPE_ADD_CHANNEL_TITLE = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_ADD_CHANNEL = 4;

    public int itemType;

    private String channelId;

    private String channelName;

    private int channelType;//0 可移除，1不可移除

    private boolean isChannelSelect;// 0 未选中 1 选中

    private boolean isChannelAdd;//频道是否添加过

    public boolean isChannelAdd() {
        return isChannelAdd;
    }

    public void setChannelAdd(boolean channelAdd) {
        isChannelAdd = channelAdd;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getChannelId() {
        return channelId == null ? "" : channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName == null ? "" : channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public boolean isChannelSelect() {
        return isChannelSelect;
    }

    public void setChannelSelect(boolean channelSelect) {
        isChannelSelect = channelSelect;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "itemType=" + itemType +
                ", channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelType=" + channelType +
                ", isChannelSelect=" + isChannelSelect +
                ", isChannelAdd=" + isChannelAdd +
                '}';
    }
}

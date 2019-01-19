package com.chexiaoya.aiyue.interfaces;

import com.chexiaoya.aiyue.bean.Channel;

/**
 * Created by xcb on 2019/1/15.
 */
public interface OnChannelChangeListener {
    void onAddChannel(Channel channel);

    void onRemoveChannel(Channel channel);


}

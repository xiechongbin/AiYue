package com.chexiaoya.cardoverlay;

/**
 * Created by xcb on 2019/3/8.
 */
enum Align {
    LEFT(1),
    RIGHT(-1),
    TOP(1),
    BOTTOM(-1);

    int layoutDirection;

    Align(int sign) {
        this.layoutDirection = sign;
    }
}

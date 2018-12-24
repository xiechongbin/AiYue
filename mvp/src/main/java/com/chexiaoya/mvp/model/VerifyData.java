package com.chexiaoya.mvp.model;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by xcb on 2018/12/24.
 */
public class VerifyData {

    private static final String userName = "li";
    private static final String password = "123456";


    /**
     * 验证账号和密码
     */
    public static void verifyData(final String inputName, final String inputPassword, final IVerifyDataCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result = false;
                if (!TextUtils.isEmpty(inputName) && !TextUtils.isEmpty(inputPassword)) {
                    if (inputName.equals(userName) && password.equals(inputPassword)) {
                        result = true;
                    }
                } else {
                    result = false;
                }
                if (callback != null) {
                    if (result) {
                        callback.onSuccess("登陆成功啦");
                    } else {
                        callback.onFailure("登陆失败");
                    }
                }
            }
        }, 4000);

    }
}

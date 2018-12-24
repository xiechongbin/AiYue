package com.chexiaoya.mvp.presenter;

import com.chexiaoya.mvp.model.IVerifyDataCallback;
import com.chexiaoya.mvp.model.VerifyData;

/**
 * Created by xcb on 2018/12/24.
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    public void login(String inputName, String inputPassword) {
        if (isAttachView()) {
            view.onShowView();
        }

        VerifyData.verifyData(inputName, inputPassword, new IVerifyDataCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (isAttachView()) {
                    view.onLoginSuccess();
                    view.onHideView();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isAttachView()) {
                    view.onLoginFailed(msg);
                    view.onHideView();
                }
            }
        });

    }

    public void clear() {
        if (view != null) {
            view.onClearText();
        }
    }
}

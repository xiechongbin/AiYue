package com.chexiaoya.mvp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chexiaoya.mvp.R;
import com.chexiaoya.mvp.presenter.ILoginView;
import com.chexiaoya.mvp.presenter.LoginPresenter;

/**
 * mvp开发模式代码测试
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, ILoginView {

    private EditText ed_account;
    private EditText ed_password;
    private Button btn_login;
    private Button btn_clear;

    private Dialog dialog;

    private LoginPresenter iLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListener();
    }

    private void findViews() {
        ed_account = findViewById(R.id.ed_account);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        btn_clear = findViewById(R.id.btn_clear);
    }


    private void setListener() {
        btn_login.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        iLoginPresenter = new LoginPresenter();
        iLoginPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (iLoginPresenter != null) {
                    iLoginPresenter.login(ed_account.getText().toString(), ed_password.getText().toString());
                }
                break;
            case R.id.btn_clear:
                if (iLoginPresenter != null) {
                    iLoginPresenter.clear();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iLoginPresenter.detachView();
    }

    @Override
    public void onClearText() {
        ed_account.setText("");
        ed_password.setText("");
    }

    @Override
    public void onShowView() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("正在验证");
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onHideView() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
    }
}

package com.di.fast.presenter;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.di.base.frame.bean.ToastMessageException;
import com.di.fast.contract.LoginContract;
import com.di.base.frame.mvp.BasePresenter;
import com.di.fast.view.activity.MainActivity;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    public LoginPresenter(LoginContract.Model model, LoginContract.View view) {
        super(model, view);
    }

    public void login(CharSequence name, CharSequence password) {
        if (TextUtils.isEmpty(name)) {
            mView.showMessage(new ToastMessageException("请输入登录名"));
        } else if (TextUtils.isEmpty(password)) {
            mView.showMessage(new ToastMessageException("请输入密码"));
        } else {
            mView.showLoading();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mView.hideLoading();
                            if (TextUtils.equals(name, "dcs") && TextUtils.equals(password, "123456")) {
                                mView.trans(MainActivity.class, null);
                            } else {
                                mView.showMessage(new ToastMessageException("账号或密码错误"));
                            }
                        }
                    });

                }
            }).start();
        }
    }

}

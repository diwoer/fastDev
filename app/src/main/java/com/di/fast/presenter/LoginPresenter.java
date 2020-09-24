package com.di.fast.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.di.base.frame.bean.ToastMessageException;
import com.di.base.net.usable.consumer.DataConsumer;
import com.di.base.net.usable.consumer.ExceptionConsumer;
import com.di.base.net.request.RequestLogin;
import com.di.base.net.response.ResponseLogin;
import com.di.base.util.SoftKeyBoardUtil;
import com.di.fast.contract.LoginContract;
import com.di.base.frame.mvp.BasePresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    public LoginPresenter(LoginContract.Model model, LoginContract.View view, Activity activity) {
        super(model, view, activity);
    }

    public void login(EditText etName, EditText etPassword) {

        if(true){
            mView.loginSuccess();
            return;
        }

        CharSequence name = etName.getText();
        CharSequence password = etPassword.getText();
        if (TextUtils.isEmpty(name)) {
            mView.showMessage(new ToastMessageException("请输入登录名"));
        } else if (TextUtils.isEmpty(password)) {
            mView.showMessage(new ToastMessageException("请输入密码"));
        } else {
            mView.showLoading();
            SoftKeyBoardUtil.closeInputMethod(getActivity());
            addDispose(getLoginDisposable(password.toString(), name.toString()));
        }
    }

    private Disposable getLoginDisposable(String password, String name) {
        return mModel.login(new RequestLogin(password, name))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new DataConsumer<ResponseLogin>() {
                    @Override
                    public void onSuccess(ResponseLogin responseLogin) {
                        final String commonErrorMsg = "账号或密码错误";
                        if (responseLogin != null) {
                            if (responseLogin.code == 200) {
                                mView.loginSuccess();
                            } else if (!TextUtils.isEmpty(responseLogin.message)) {
                                mView.showMessage(new ToastMessageException(responseLogin.message));
                            } else {
                                mView.showMessage(new ToastMessageException(commonErrorMsg));
                            }
                        } else {
                            mView.showMessage(new ToastMessageException(commonErrorMsg));
                        }
                        mView.hideLoading();
                    }
                }, new ExceptionConsumer() {
                    @Override
                    public void onError(Throwable e) {
                        mView.showMessage(e);
                    }
                });
    }

}

package com.di.fast.contract;

import com.di.base.frame.mvp.IModel;
import com.di.base.frame.mvp.IView;
import com.di.base.net.request.RequestLogin;
import com.di.base.net.response.ResponseLogin;

import io.reactivex.rxjava3.core.Observable;

public interface LoginContract {

    interface View extends IView{

        /**
         * 登录成功
         * */
        void loginSuccess();
    }

    interface Model extends IModel{
        /**
         * 登录
         * */
        Observable<ResponseLogin> login(RequestLogin requestLogin);
    }
}

package com.di.fast.model;

import com.di.base.net.usable.RetrofitManager;
import com.di.base.net.request.RequestLogin;
import com.di.base.net.response.ResponseLogin;
import com.di.fast.contract.LoginContract;
import com.di.base.frame.mvp.BaseModel;

import io.reactivex.rxjava3.core.Observable;

public class LoginModel extends BaseModel implements LoginContract.Model {

    @Override
    public Observable<ResponseLogin> login(RequestLogin requestLogin) {
        return RetrofitManager.getInstance().getCommonService().login(requestLogin);
    }
}

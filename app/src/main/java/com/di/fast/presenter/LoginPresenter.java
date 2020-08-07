package com.di.fast.presenter;

import com.di.fast.contract.LoginContract;
import com.di.base.frame.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {

    @Override
    protected Class<LoginContract.Model> getModelClass() {
        return LoginContract.Model.class;
    }

    @Override
    protected Class<LoginContract.View> getViewClass() {
        return LoginContract.View.class;
    }



}

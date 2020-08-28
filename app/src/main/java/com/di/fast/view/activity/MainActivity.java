package com.di.fast.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.di.fast.R;
import com.di.fast.contract.LoginContract;
import com.di.fast.presenter.LoginPresenter;
import com.di.base.frame.mvp.base.ActivityPresenterView;

public class MainActivity extends ActivityPresenterView<LoginPresenter> implements LoginContract.View {

    @Override
    public int getRootView() {
        return R.layout.activity_main;
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {
        Glide.with(this).load("").into(new ImageView(this));
    }

    @Override
    protected LoginPresenter getPresenter() {
        return null;
    }

    @Override
    public void outDestroy() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(Throwable e) {

    }

    @Override
    public void loginSuccess() {

    }
}
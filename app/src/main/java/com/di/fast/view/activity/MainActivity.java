package com.di.fast.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.di.base.log.DLog;
import com.di.fast.R;
import com.di.fast.contract.LoginContract;
import com.di.fast.model.LoginModel;
import com.di.fast.presenter.LoginPresenter;
import com.di.base.frame.mvp.base.ActivityPresenterView;
import com.zjmy.rxbus.OnEventListener;
import com.zjmy.rxbus.RxBus;
import com.zjmy.rxbus.RxMessage;

public class MainActivity extends ActivityPresenterView<LoginPresenter> implements LoginContract.View {

    @Override
    public int getRootView() {
        return R.layout.activity_main;
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {

        RxBus.getInstance().register(this, new OnEventListener() {
            @Override
            public void onEvent(RxMessage message) {
                DLog.e("[RxBus] [onEvent] " + message.code);
            }
        });

        get(R.id.btn_rxbus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post(new RxMessage(1));
                RxBus.getInstance().post(new RxMessage(2));
                RxBus.getInstance().post(new RxMessage(3));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DLog.e("MainActivity");
        RxBus.getInstance().lookSubInfo();
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(new LoginModel(), this, this);
    }

    @Override
    public void outDestroy() {
        RxBus.getInstance().unRegister(this);
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
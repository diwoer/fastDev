package com.di.fast.view.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.di.base.frame.bean.ToastMessageException;
import com.di.base.frame.mvp.base.ActivityPresenterView;
import com.di.base.log.DLog;
import com.di.base.widget.et.IconEditText;
import com.di.demo.view.activity.FileDownloadActivity;
import com.di.fast.R;
import com.di.fast.contract.LoginContract;
import com.di.fast.model.LoginModel;
import com.di.fast.presenter.LoginPresenter;
import com.di.fast.state.StateView;
import com.di.fast.state.listener.OnRetryListener;
import com.di.file.FileTestActivity;
import com.zjmy.rxbus.OnEventListener;
import com.zjmy.rxbus.RxBus;
import com.zjmy.rxbus.RxMessage;

import java.util.Set;

public class LoginActivity extends ActivityPresenterView<LoginPresenter> implements LoginContract.View {

    private StateView stateView;
    private IconEditText etLoginName;
    private IconEditText etLoginPassword;
    private Button btnLogin;
    private Button btnCreateFile;

    @Override
    public void showLoading() {
        stateView.showLoadingLayout();
    }

    @Override
    public void hideLoading() {
        stateView.showDataLayout();
        btnLogin.setEnabled(true);
    }

    @Override
    public void showMessage(Throwable e) {
        if(e instanceof ToastMessageException){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            stateView.showLayoutByException(e);
        }
        btnLogin.setEnabled(true);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_login;
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {

        RxBus.getInstance().register(this, new OnEventListener() {
            @Override
            public void onEvent(RxMessage message) {
                DLog.e("[RxBus] [onEvent] " + message.code);
            }
        });

        stateView = get(R.id.state_view);
        etLoginName = get(R.id.et_login_name);
        etLoginPassword = get(R.id.et_login_password);
        btnLogin = get(R.id.btn_login);
        btnCreateFile = get(R.id.btn_create_file);

        stateView.setRetryListener(new OnRetryListener() {
            @Override
            public void retry(String state) {
                hideLoading();
            }
        });

        bindSingleClicks(new int[]{
            R.id.btn_login, R.id.btn_create_file
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DLog.e("LoginActivity");
        RxBus.getInstance().lookSubInfo();
    }

    @Override
    public void outDestroy() {
        super.outDestroy();
        RxBus.getInstance().unRegister(this);
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(new LoginModel(), this, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            btnLogin.setEnabled(false);
            mPresenter.login(etLoginName, etLoginPassword);
        } else if (v.getId() == R.id.btn_create_file){
            startActivity(new Intent(this, FileTestActivity.class));
        }
    }

    @Override
    public void loginSuccess() {

        trans(FileDownloadActivity.class, null);
        btnLogin.setEnabled(true);
    }
}
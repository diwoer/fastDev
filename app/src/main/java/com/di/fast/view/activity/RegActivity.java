package com.di.fast.view.activity;

import android.os.Bundle;

import com.di.fast.R;
import com.di.fast.contract.RegContract;
import com.di.fast.presenter.RegPresenter;
import com.di.fast.model.RegModel;
import com.di.base.frame.mvp.base.ActivityPresenterView;

public class RegActivity extends ActivityPresenterView<RegPresenter> implements RegContract.View {

    @Override
    public int getRootView() {
        return R.layout.activity_reg;
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {

    }

    @Override
    protected RegPresenter getPresenter() {
        return new RegPresenter(new RegModel(), this, this);
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
}
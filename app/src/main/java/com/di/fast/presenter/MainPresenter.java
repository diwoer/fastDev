package com.di.fast.presenter;

import android.app.Activity;

import com.di.base.frame.mvp.BasePresenter;
import com.di.fast.contract.MainContract;

public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    public MainPresenter(MainContract.Model model, MainContract.View view, Activity activity) {
        super(model, view, activity);
    }

}

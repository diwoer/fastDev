package com.di.fast.presenter;

import android.app.Activity;

import com.di.base.frame.mvp.BasePresenter;
import com.di.fast.contract.RegContract;

public class RegPresenter extends BasePresenter<RegContract.Model, RegContract.View> {

    public RegPresenter(RegContract.Model model, RegContract.View view, Activity activity) {
        super(model, view, activity);
    }

}

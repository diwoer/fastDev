package com.di.base.viewcontroller;

import android.view.View;

public class ViewControllerCommonImp implements IViewControllerCommon {

    private View mContentView;

    public ViewControllerCommonImp(View contentView){
        this.mContentView = contentView;
    }

    @Override
    public <T extends View> T get(int resId) {
        return (T) mContentView.findViewById(resId);
    }
}

package com.di.base.viewcontrol.viewcontroller;

import android.view.View;

public interface IViewControllerLifeCycleClick {

    //重置点击事件
    void onResetClickViews();

    //限制点击
    void addClickViews(View[] views);
}

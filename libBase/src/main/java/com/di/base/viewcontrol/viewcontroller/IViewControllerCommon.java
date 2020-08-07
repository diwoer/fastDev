package com.di.base.viewcontrol.viewcontroller;

import android.view.View;

public interface IViewControllerCommon {

    <T extends View> T get(int resId);
}

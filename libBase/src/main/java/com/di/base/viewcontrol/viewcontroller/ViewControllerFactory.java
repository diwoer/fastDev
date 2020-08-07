package com.di.base.viewcontrol.viewcontroller;

import android.view.View;

public class ViewControllerFactory implements IViewControllerClick, IViewControllerCommon, IViewControllerLifeCycleClick{

    private ViewControllerCommonImp mViewControllerCommonImp;
    private ViewControllerClickImp mViewControllerClickImp;
    private ViewControllerLifeCycleClickImp mViewControllerLifeCycleClickImp;

    public ViewControllerFactory(View contentView, View.OnClickListener onClickListener) {
        mViewControllerCommonImp = new ViewControllerCommonImp(contentView);
        mViewControllerClickImp = new ViewControllerClickImp(contentView, onClickListener);
        mViewControllerLifeCycleClickImp = new ViewControllerLifeCycleClickImp(onClickListener);
    }

    @Override
    public void bindSingleClicks(int[] resIds) {
        mViewControllerClickImp.bindSingleClicks(resIds);
    }

    @Override
    public void bindSingleClicks(int[] resIds, int seconds) {
        mViewControllerClickImp.bindSingleClicks(resIds, seconds);
    }

    @Override
    public void resetSingleClicks() {
        mViewControllerClickImp.resetSingleClicks();
    }

    @Override
    public void resetSingleClicks(int[] resIds) {
        mViewControllerClickImp.resetSingleClicks(resIds);
    }

    @Override
    public void bindNormalClicks(int[] resIds) {
        mViewControllerClickImp.bindNormalClicks(resIds);
    }

    @Override
    public <T extends View> T get(int resId) {
        return (T) mViewControllerCommonImp.get(resId);
    }

    @Override
    public void onResetClickViews() {
        mViewControllerLifeCycleClickImp.onResetClickViews();
    }

    @Override
    public void addClickViews(View[] views) {
        mViewControllerLifeCycleClickImp.addClickViews(views);
    }
}

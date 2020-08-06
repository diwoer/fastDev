package com.di.frame.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.di.base.viewcontroller.IViewControllerClick;
import com.di.base.viewcontroller.IViewControllerCommon;
import com.di.base.viewcontroller.ViewControllerFactory;
import com.di.frame.mvp.IPresenter;
import com.di.frame.mvp.IView;

public abstract class ActivityPresenterView<P extends IPresenter> extends AppCompatActivity
        implements IView, UIListener, LifecycleOwner, IViewControllerCommon, IViewControllerClick, View.OnClickListener {

    @Nullable
    protected P mPresenter;

    /**
     * 当前Activity的根View
     * */
    private View mContentView;

    /**
     * 实现了View的双击控制
     * 实现了根据resId获取对应的View
     * 实现了一个Activity同时只能点击一个View
     * */
    private ViewControllerFactory mViewControllerFactory;

    /**
     * 绑定presenter
     * */
    protected abstract Class<P> getPresenterClass();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mPresenter = getPresenterClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        mContentView = getLayoutInflater().inflate(getRootView(), null);

        setContentView(mContentView);

        mViewControllerFactory = new ViewControllerFactory(mContentView, this);

        outCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        outDestroy();
        if(mPresenter != null){
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void trans(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void transForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishSelf() {
        finish();
    }

    @Override
    public <T extends View> T get(int resId) {
        return mViewControllerFactory.get(resId);
    }

    @Override
    public void bindNormalClicks(int[] resIds) {
        mViewControllerFactory.bindNormalClicks(resIds);
    }

    @Override
    public void bindSingleClicks(int[] resIds) {
        mViewControllerFactory.bindSingleClicks(resIds);
    }

    @Override
    public void bindSingleClicks(int[] resIds, int seconds) {
        mViewControllerFactory.bindSingleClicks(resIds, seconds);
    }

    @Override
    public void resetSingleClicks() {
        mViewControllerFactory.resetSingleClicks();
    }

    @Override
    public void resetSingleClicks(int[] resIds) {
        mViewControllerFactory.resetSingleClicks(resIds);
    }

    @Override
    public void onClick(View v) {

    }
}

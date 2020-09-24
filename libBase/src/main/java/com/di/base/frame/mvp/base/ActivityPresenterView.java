package com.di.base.frame.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.di.base.R;
import com.di.base.tool.StatusBarTool;
import com.di.base.viewcontrol.viewcontroller.IViewControllerClick;
import com.di.base.viewcontrol.viewcontroller.IViewControllerCommon;
import com.di.base.viewcontrol.viewcontroller.ViewControllerFactory;
import com.di.base.frame.mvp.IPresenter;
import com.di.base.frame.mvp.IView;

public abstract class ActivityPresenterView<P extends IPresenter> extends AppCompatActivity
        implements IView, UIListener, LifecycleOwner, IViewControllerCommon, IViewControllerClick, View.OnClickListener {

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
    protected abstract P getPresenter();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContentView = getLayoutInflater().inflate(getRootView(), null);

        setContentView(mContentView);

        mPresenter = getPresenter();
        mPresenter.onStart();

        mViewControllerFactory = new ViewControllerFactory(mContentView, this);

        StatusBarTool.getInstance().setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.holo_blue_light));
        StatusBarTool.getInstance().setStatusBarMode(this, false);

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
    public void outDestroy() {

    }

    @Override
    public void trans(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void transForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishAct() {
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

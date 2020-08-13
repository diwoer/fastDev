package com.di.base.frame.mvp;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.di.module.eventbus.EventBus;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {

    protected Activity mActivity;

    protected M mModel;
    protected V mView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(M m, V v, Activity activity){
        this.mModel = m;
        this.mView = v;
        this.mActivity = activity;
        onStart();
    }

    /**
     * 使用EventBus
     *
     * @return true 要使用；false 不使用；默认不使用
     * */
    public boolean useEventBus(){
        return false;
    }

    public Activity getActivity(){
        return mActivity;
    }

    @Override
    public void onStart() {

        if(mView != null && mView instanceof LifecycleOwner){
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
            if(mModel != null && mModel instanceof LifecycleOwner){
                ((LifecycleOwner) mView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }

        if(useEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 添加 Rx 流程，为了之后统一释放
     * */
    public void addDispose(Disposable disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 释放 Rx 流程
     * */
    public void unDispose(){
        if(mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroy() {
        if(useEventBus()){
            EventBus.getDefault().unRegister(this);
        }
        unDispose();
        if(mModel != null){
            mModel.onDestroy();
        }
        mModel = null;
        mView = null;
        mCompositeDisposable = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner){
        owner.getLifecycle().removeObserver(this);
    }
}

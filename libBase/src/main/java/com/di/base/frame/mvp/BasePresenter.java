package com.di.base.frame.mvp;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.di.module.eventbus.EventBus;

public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {

    protected M mModel;
    protected V mView;

    public BasePresenter(M m, V v){
        this.mModel = m;
        this.mView = v;
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

    @Override
    public void onDestroy() {
        if(useEventBus()){
            EventBus.getDefault().unRegister(this);
        }
        if(mModel != null){
            mModel.onDestroy();
        }
        mModel = null;
        mView = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner){
        owner.getLifecycle().removeObserver(this);
    }
}

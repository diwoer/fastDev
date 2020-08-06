package com.di.frame.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.di.module.eventbus.EventBus;

public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {

    protected M mModel;
    protected V mView;

    /**
     * 设置要绑定的model
     * */
    protected abstract Class<M> getModelClass();

    /**
     * 设置要绑定的view
     * */
    protected abstract Class<V> getViewClass();

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

        try {
            mModel = getModelClass().newInstance();
            mView = getViewClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

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

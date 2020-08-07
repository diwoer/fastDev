package com.di.base.frame.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.di.base.frame.mvp.IPresenter;
import com.di.base.frame.mvp.IView;
import com.di.base.viewcontrol.viewcontroller.IViewControllerClick;
import com.di.base.viewcontrol.viewcontroller.IViewControllerCommon;
import com.di.base.viewcontrol.viewcontroller.ViewControllerFactory;

public abstract class FragmentPresenterView<P extends IPresenter> extends Fragment
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getRootView(), container, false);
        return mContentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mPresenter = getPresenterClass().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

        mViewControllerFactory = new ViewControllerFactory(mContentView, this);

        outCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        outDestroy();
        if(mPresenter != null){
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void trans(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void transForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishAct() {
        getActivity().finish();
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
    public void bindNormalClicks(int[] resIds) {
        mViewControllerFactory.bindNormalClicks(resIds);
    }

    @Override
    public <T extends View> T get(int resId) {
        return mViewControllerFactory.get(resId);
    }
}
package com.di.base.viewcontroller;

import android.view.View;

import com.di.base.viewlistener.OnSingleClickListener;

import java.util.HashMap;

public class ViewControllerClickImp implements IViewControllerClick {

    private View mContentView;
    private View.OnClickListener mOnClickListener;
    private ViewControllerCommonImp mViewControllerCommonImp;

    private HashMap<Integer, Integer> clicksTimeMap;

    public ViewControllerClickImp(View contentView, View.OnClickListener listener){
        this.mContentView = contentView;
        this.mOnClickListener = listener;
        mViewControllerCommonImp = new ViewControllerCommonImp(mContentView);
        clicksTimeMap = new HashMap<>();
    }

    @Override
    public void bindSingleClicks(int[] resIds) {
        for(int resId : resIds){
            addSingleClicks(resId, OnSingleClickListener.Click_Limit_Time);
            mViewControllerCommonImp.get(resId).setOnClickListener(new OnSingleClickListener(mOnClickListener));
        }
    }

    @Override
    public void bindSingleClicks(int[] resIds, int seconds) {
        for(int resId : resIds){
            addSingleClicks(resId, seconds);
            mViewControllerCommonImp.get(resId).setOnClickListener(new OnSingleClickListener(mOnClickListener, seconds));
        }
    }

    @Override
    public void resetSingleClicks() {
        if(clicksTimeMap != null && clicksTimeMap.size() > 0){
            for(int resId : clicksTimeMap.keySet()){
                mViewControllerCommonImp.get(resId).setOnClickListener(new OnSingleClickListener(mOnClickListener, clicksTimeMap.get(resId)));
            }
        }
    }

    @Override
    public void resetSingleClicks(int[] resIds) {
        if(clicksTimeMap != null && clicksTimeMap.size() > 0){
            for(int resId : resIds){
                if(clicksTimeMap.containsKey(resId)){
                    mViewControllerCommonImp.get(resId).setOnClickListener(new OnSingleClickListener(mOnClickListener, clicksTimeMap.get(resId)));
                }
            }
        }
    }

    @Override
    public void bindNormalClicks(int[] resIds) {
        for(int resId : resIds){
            mViewControllerCommonImp.get(resId).setOnClickListener(mOnClickListener);
        }
    }

    private void addSingleClicks(int resIds, int seconds){
        if(clicksTimeMap == null){
            clicksTimeMap = new HashMap<>();
        }
        clicksTimeMap.put(resIds, seconds);
    }
}

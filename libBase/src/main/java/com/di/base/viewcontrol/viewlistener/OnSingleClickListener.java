package com.di.base.viewcontrol.viewlistener;

import android.view.View;

/**
 * 控制View的单击事件
 * */
public class OnSingleClickListener implements View.OnClickListener {

    private long lastClickTime;
    public static final int Click_Limit_Time = 1000;
    private int mClickLimitTimeStamp;

    private View.OnClickListener mSingleClickListener;

    public OnSingleClickListener(View.OnClickListener singleClickListener){
        mSingleClickListener = singleClickListener;
        mClickLimitTimeStamp = Click_Limit_Time;
    }

    public OnSingleClickListener(View.OnClickListener singleClickListener, int Click_Limit_Time){
        mSingleClickListener = singleClickListener;
        this.mClickLimitTimeStamp = Click_Limit_Time;
    }

    @Override
    public void onClick(View view) {
        if(System.currentTimeMillis() - lastClickTime >=  mClickLimitTimeStamp){
            lastClickTime = System.currentTimeMillis();
            mSingleClickListener.onClick(view);
        }
    }
}

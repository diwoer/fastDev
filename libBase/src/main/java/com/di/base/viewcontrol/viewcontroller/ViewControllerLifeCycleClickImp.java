package com.di.base.viewcontrol.viewcontroller;

import android.view.View;

public class ViewControllerLifeCycleClickImp implements IViewControllerLifeCycleClick {

    private View currentClickView;
    private View.OnClickListener mOnClickListener;

    private boolean isLimit;

    public ViewControllerLifeCycleClickImp(View.OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void addClickViews(View[] views){
        for(View view : views){
            view.setOnClickListener(getLimitClickListener(view, mOnClickListener));
        }
        isLimit = false;
    }

    private View.OnClickListener getLimitClickListener(final View view, final View.OnClickListener doClickListener){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLimit){
                    return;
                }
                doClickListener.onClick(view);
                isLimit = true;
                currentClickView = view;
            }
        };
    }

    @Override
    public void onResetClickViews() {
        if(currentClickView != null){
            currentClickView.setOnClickListener(getLimitClickListener(currentClickView, mOnClickListener));
        }
        isLimit = false;
    }
}

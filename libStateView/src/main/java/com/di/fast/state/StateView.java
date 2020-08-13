package com.di.fast.state;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.di.fast.state.child.DefaultStateView;
import com.di.fast.state.exception.EmptyException;
import com.di.fast.state.listener.OnRetryListener;

import java.net.SocketException;
import java.net.SocketTimeoutException;

public class StateView extends FrameLayout {

    private DefaultStateView mDefaultStateView;
    private View mLoadingStateView;

    private View mContentView;

    private OnRetryListener onRetryListener;
    private String mTips;

    public StateView(@NonNull Context context) {
        this(context, null);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mDefaultStateView = (DefaultStateView) inflater.inflate(R.layout.layout_default_state_view, null);
        mLoadingStateView = inflater.inflate(R.layout.view_state_loading_layout, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() == 1){
            mContentView = getChildAt(0);
            addView(mDefaultStateView);
            addView(mLoadingStateView);

            showLayoutByState(STATE.STATE_CONTENT);
        }else {
            throw new RuntimeException("StateView Must Have One Child View In Layout XML !");
        }
    }

    public static class STATE{

        public static final String STATE_LOADING = "STATE_LOADING";
        public static final String STATE_EMPTY = "STATE_EMPTY";
        public static final String STATE_NET_ERROR = "STATE_NET_ERROR";
        public static final String STATE_COMMON_ERROR = "STATE_COMMON_ERROR";

        public static final String STATE_CONTENT = "STATE_CONTENT";
    }

    public void enableTransparentLoadingView(boolean isTransparent){
        if(isTransparent){
            mLoadingStateView.setBackgroundColor(Color.TRANSPARENT);
        }else {
            mLoadingStateView.setBackgroundColor(Color.WHITE);
        }
    }

    public void setRetryListener(OnRetryListener listener){
        this.onRetryListener = listener;
    }

    public void setTips(String tips){
        mTips = tips;
    }

    public void showLoadingLayout(){
        showLayoutByState(STATE.STATE_LOADING);
    }

    public void showEmptyLayout(){
        showLayoutByState(STATE.STATE_EMPTY);
        mDefaultStateView.showEmptyLayout(mTips);
    }

    public void showEmptyLayout(String message){
        showLayoutByState(STATE.STATE_EMPTY);
        mDefaultStateView.showEmptyLayout(message);
    }

    public void showNetErrorLayout(){
        showLayoutByState(STATE.STATE_NET_ERROR);
        mDefaultStateView.showNetErrorLayout(onRetryListener);
    }

    public void showCommonErrorLayout(){
        showLayoutByState(STATE.STATE_COMMON_ERROR);
        mDefaultStateView.showCommonErrorLayout(onRetryListener);
    }

    public void showDataLayout(){
        showLayoutByState(STATE.STATE_CONTENT);
    }

    public void showLayoutByException(Throwable e){
        if(e instanceof SocketException){//网络请求错误界面
            showNetErrorLayout();
        }else if(e instanceof SocketTimeoutException){//网络请求超时界面
            showNetErrorLayout();
        }else if(e instanceof EmptyException){//空数据界面
            if(TextUtils.isEmpty(e.getMessage())){
                showEmptyLayout();
            }else {
                showEmptyLayout(e.getMessage());
            }
        }else {//通用错误界面
            showCommonErrorLayout();
        }
    }

    private void showLayoutByState(String state){
        switch (state){
            case STATE.STATE_LOADING:
                mContentView.setVisibility(VISIBLE);
                mDefaultStateView.setVisibility(GONE);
                mLoadingStateView.setVisibility(VISIBLE);
                break;
            case STATE.STATE_EMPTY:
            case STATE.STATE_NET_ERROR:
            case STATE.STATE_COMMON_ERROR:
                mContentView.setVisibility(GONE);
                mDefaultStateView.setVisibility(VISIBLE);
                mLoadingStateView.setVisibility(GONE);
                break;
            case STATE.STATE_CONTENT:
                mContentView.setVisibility(VISIBLE);
                mDefaultStateView.setVisibility(GONE);
                mLoadingStateView.setVisibility(GONE);
                break;
        }
    }
}

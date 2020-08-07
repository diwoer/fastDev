package com.di.fast.state.manger;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class StateViewManger {

    private volatile static StateViewManger sInstance;

    private StateViewManger(){}

    public static StateViewManger instance(){
        if(sInstance == null){
            synchronized (StateViewManger.class){
                if(sInstance == null){
                    sInstance = new StateViewManger();
                }
            }
        }
        return sInstance;
    }

    public static final int STUDENT = 1;
    public static final int TEACHER = 0;
    private int reLoadButtonType = STUDENT; // 0教师端；1学生端

    public void setReLoadBtnType(int type){
        this.reLoadButtonType = type;
    }

    public int getReLoadBtnType(){
        return reLoadButtonType;
    }

    public static final int LOADING = 0;
    public static final int LOAD_SUCCESS = 1;
    public static final int LOAD_FAIL = 2;
    public static final int EMPTY_DATA = 3;

    public interface Adapter{

        View getView(Holder holder, View convertView, int status);
    }

    public static class Holder{

        private Adapter mAdapter;
        private Context mContext;
        private ViewGroup mWrapper;

        private int mCurStatus;
        private SparseArray<View> mStatusViews = new SparseArray<>();
        private View mCurStatusView;

        private Runnable mRetryTask;
        private Throwable mException; // 显示异常内容
        private int emptyType = 0;
        private int reLoadButtonType; // 0教师端；1学生端

        public Holder(Adapter adapter, Context context, ViewGroup wrapper) {
            this.mAdapter = adapter;
            this.mContext = context;
            this.mWrapper = wrapper;
        }

        public void showLoadingView(){
            showStatusView(LOADING);
        }

        public void showLoadSuccessView(){
            showStatusView(LOAD_SUCCESS);
        }

        public void showLoadFailView(){
            showStatusView(LOAD_FAIL);
        }

        public void showEmptyView(){
            showStatusView(EMPTY_DATA);
        }

        private void showStatusView(int status){
            if(mCurStatus == status && !validate()){
                return;
            }

            mCurStatus = status;
            View convertView = mStatusViews.get(status);
            if(convertView == null){
                convertView = mCurStatusView;
            }

            View view = mAdapter.getView(this, convertView, status);
            if(view == null){
                return;
            }

            if(view != mCurStatusView || mWrapper.indexOfChild(view) < 0){
                if(mCurStatusView != null){
                    mWrapper.removeView(mCurStatusView);
                }
                mWrapper.addView(view);
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                if(lp != null){
                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                }
            }else if(mWrapper.indexOfChild(view) != mWrapper.getChildCount() - 1){
                view.bringToFront();
            }

            mCurStatusView = view;
            mStatusViews.put(status, view);
        }

        public Holder withRetry(Runnable task){
            this.mRetryTask = task;
            return this;
        }

        public Holder withEmptyData(int type){
            this.emptyType = type;
            return this;
        }

        public Holder withReLoadButtonType(int type){
            this.reLoadButtonType = type;
            return this;
        }

        public Runnable getRetryTask(){
            return mRetryTask;
        }

        public int getEmptyData(){
            return emptyType;
        }

        public int getReLoadButtonType(){
            return reLoadButtonType;
        }

        public Context getContext(){
            return mContext;
        }

        public void setException(Throwable e){
            this.mException = e;
        }

        public Throwable getException(){
            return mException;
        }

        private boolean validate(){
            return mAdapter != null && mContext != null && mWrapper != null;
        }

    }

}

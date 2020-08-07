package com.di.base.frame.mvp.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * 点击事件
     * */
    private OnViewClickListener mOnViewClickListener;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    /**
     * 添加数据
     * */
    public abstract void setData(@NonNull T data, int position);

    /**
     * 释放资源，暂时没有想到要释放的资源，后续添加
     * */
    public void onRelease(){

    }

    @Override
    public void onClick(View v) {
        if(mOnViewClickListener != null){
            mOnViewClickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnViewClickListener(OnViewClickListener onViewClickListener){
        this.mOnViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener{

        /**
         * item view 被点击
         *
         * @param clickView 被点击的View
         * @param position 被点击的item在列表中的位置
         * */
        void onClick(View clickView, int position);
    }
}

package com.di.base.frame.mvp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {

    private List<T> mDataList;
    private Context context;

    private OnRecyclerItemClickListener<T> mOnRecyclerItemClickListener;

    public BaseAdapter(Context context) {
        this.context = context;
        this.mDataList = new ArrayList<>();
    }

    /**
     * 遍历所有 {@link BaseHolder}, 释放他们需要释放的资源
     *
     * @param recyclerView {@link RecyclerView}
     */
    public static void releaseAllHolder(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        for (int i = recyclerView.getChildCount() - 1; i >= 0; i--) {
            final View view = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof BaseHolder) {
                ((BaseHolder) viewHolder).onRelease();
            }
        }
    }

    /**
     * 设置数据
     */
    public void setData(List<T> dataList) {
        int positionStart = this.mDataList.size();
        this.mDataList.addAll(dataList);
        notifyItemRangeInserted(positionStart, dataList.size());
    }

    /**
     * 清空已有数据
     */
    public void refreshData() {
        this.mDataList.clear();
    }

    @NonNull
    @Override
    public BaseHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(context).inflate(getRootView(viewType), parent, false);
        BaseHolder<T> mHolder = getViewHolder(itemView, viewType);
        mHolder.setOnViewClickListener(new BaseHolder.OnViewClickListener() {
            @Override
            public void onClick(View clickView, int position) {
                if (mOnRecyclerItemClickListener != null) {
                    mOnRecyclerItemClickListener.onClick(mHolder, clickView, viewType, getItem(position), position);
                }
            }
        });
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<T> holder, int position) {
        holder.setData(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public T getItem(int position) {
        return position < mDataList.size() ? mDataList.get(position) : null;
    }

    public abstract int getRootView(int viewType);

    public abstract BaseHolder<T> getViewHolder(View view, int viewType);

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<T> onRecyclerItemClickListener) {
        this.mOnRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public interface OnRecyclerItemClickListener<T> {

        void onClick(BaseHolder<T> holder, View view, int viewType, T data, int position);
    }
}

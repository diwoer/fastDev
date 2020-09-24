package com.di.demo.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.di.base.frame.mvp.base.BaseAdapter;
import com.di.base.frame.mvp.base.BaseHolder;
import com.di.demo.R;
import com.di.demo.data.bean.FileDownloadBean;

import java.util.HashMap;
import java.util.Locale;

public class FileDownloadAdapter extends BaseAdapter<FileDownloadBean> {

    private HashMap<Integer, ViewHolder> viewHolderMap;

    public FileDownloadAdapter(Context context) {
        super(context);
        viewHolderMap = new HashMap<>();
    }

    public ViewHolder getHolder(int position){
        return viewHolderMap.get(position);
    }

    public void delete(int position){
        getDataList().remove(position);
        notifyItemChanged(position);
    }

    @Override
    public void refreshData() {
        super.refreshData();
        viewHolderMap.clear();
    }

    @Override
    public int getRootView(int viewType) {
        return R.layout.item_file_download_list;
    }

    @Override
    public BaseHolder<FileDownloadBean> getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<FileDownloadBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        viewHolderMap.put(position, (ViewHolder)holder);
    }

    public static class ViewHolder extends BaseHolder<FileDownloadBean> {

        private TextView tvName;
        private ProgressBar progressBar;
        private TextView tvDownload;
        private TextView tvDelete;
        private TextView tvError;
        private TextView tvProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            progressBar = itemView.findViewById(R.id.progress_bar);
            tvDownload = itemView.findViewById(R.id.tv_download);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvError = itemView.findViewById(R.id.tv_error);
            tvProgress = itemView.findViewById(R.id.tv_progress);
        }

        @Override
        public void setData(@NonNull FileDownloadBean data, int position) {

            tvName.setText(data.name);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            tvProgress.setText("0%");
            tvError.setVisibility(View.GONE);

            tvDownload.setOnClickListener(this);
            tvDelete.setOnClickListener(this);
        }

        public void setProgress(int percent) {
            tvError.setVisibility(View.GONE);
            progressBar.setProgress(percent);
            tvProgress.setText(String.format(Locale.getDefault(), "%d%%", percent));
            if(percent == 0){
                tvDownload.setText("开始下载");
                tvDownload.setEnabled(false);
            } else if (percent == 100) {
                tvDownload.setText("下载完成");
                tvDownload.setEnabled(false);
            } else {
                tvDownload.setText("暂停");
                tvDownload.setEnabled(true);
            }
        }

        public void setError(String errorDescription) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(errorDescription);
            tvDownload.setText("重新下载");
            tvDownload.setEnabled(true);
        }

        public void fileHadExist(String path){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(String.format("文件已存在：\n%s", path));
            tvDownload.setText("下载完成");
            tvDownload.setEnabled(false);
            progressBar.setProgress(100);
            tvProgress.setText("100%");
        }

        public void downloadComplete(String path){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(String.format("下载完成，文件路径：\n%s", path));
            tvDownload.setText("下载完成");
            tvDownload.setEnabled(false);
            progressBar.setProgress(100);
            tvProgress.setText("100%");
        }
    }
}

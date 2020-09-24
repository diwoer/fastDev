package com.di.demo.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.di.base.frame.bean.ToastMessageException;
import com.di.base.frame.mvp.base.ActivityPresenterView;
import com.di.base.frame.mvp.base.BaseAdapter;
import com.di.base.frame.mvp.base.BaseHolder;
import com.di.base.log.DLog;
import com.di.demo.DLError;
import com.di.demo.DLProgress;
import com.di.demo.DLSuccess;
import com.di.demo.DownloadListener;
import com.di.demo.DownloadService;
import com.di.demo.FileCreator;
import com.di.demo.R;
import com.di.demo.contract.FileDownloadContract;
import com.di.demo.data.bean.FileDownloadBean;
import com.di.demo.data.bean.UrlPositionBean;
import com.di.demo.model.FileDownloadModel;
import com.di.demo.presenter.FileDownloadPresenter;
import com.di.demo.view.adapter.FileDownloadAdapter;

import java.util.List;

public class FileDownloadActivity extends ActivityPresenterView<FileDownloadPresenter> implements FileDownloadContract.View{

    private FileDownloadAdapter fileDownloadAdapter;

    /**
     * 下载服务binder
     * */
    private DownloadService.DownloadBinder downloadBinder;

    private class UrlServiceConnection implements ServiceConnection{

        //下载链接
        private UrlPositionBean urlPosition;

        public UrlServiceConnection(UrlPositionBean urlPosition){
            this.urlPosition = urlPosition;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DLog.e("onServiceConnected");
            downloadBinder = (DownloadService.DownloadBinder) service;
            downloadBinder.setDownloadCallback(downloadListener);
            downloadBinder.startDownload(urlPosition);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            DLog.e("onServiceDisconnected");
        }
    }

    private UrlServiceConnection downloadServiceConnection;


    /**
     * 下载回调
     */
    private final DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onProgress(DLProgress dlProgress) {
            fileDownloadAdapter.getHolder(dlProgress.urlPosition.getPosition()).setProgress(dlProgress.percentProgress);
        }

        @Override
        public void onPause(UrlPositionBean urlPosition) {

        }

        @Override
        public void onCancel(UrlPositionBean urlPosition) {

        }

        @Override
        public void onSuccess(DLSuccess success) {
            if(success.code == DLSuccess.DL_SUCCESS_CODE_EXIST){
                fileDownloadAdapter.getHolder(success.urlPosition.getPosition()).fileHadExist(success.path);
            } else {
                fileDownloadAdapter.getHolder(success.urlPosition.getPosition()).downloadComplete(success.path);
            }
        }

        @Override
        public void onError(DLError error) {
            fileDownloadAdapter.getHolder(error.urlPosition.getPosition()).setError(error.description);
        }
    };

    /**
     * 启动服务的intent，用于关闭
     * */
    private Intent intent;

    @Override
    protected FileDownloadPresenter getPresenter() {
        return new FileDownloadPresenter(new FileDownloadModel(), this, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(Throwable e) {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_file_download;
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {

        TextView tvTitle = get(R.id.tv_title);
        RecyclerView recyclerView = get(R.id.recycler_view);
        tvTitle.setText("我的下载");

        fileDownloadAdapter = new FileDownloadAdapter(this);
        recyclerView.setAdapter(fileDownloadAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileDownloadAdapter.setOnRecyclerItemClickListener(new BaseAdapter.OnRecyclerItemClickListener<FileDownloadBean>() {
            @Override
            public void onClick(BaseHolder<FileDownloadBean> holder, View view, int viewType, FileDownloadBean data, int position) {
                int viewId = view.getId();
                if(viewId == R.id.tv_download){
                    if(null == downloadBinder){
                        bindService(new UrlPositionBean(data.url, position));
                    } else {
                        downloadBinder.startDownload(new UrlPositionBean(data.url, position));
                    }
                } else if(viewId == R.id.tv_delete){
                    if(downloadBinder != null){
                        downloadBinder.cancelDownload();
                    }
                    new FileCreator(data.url).delete();
                    fileDownloadAdapter.delete(position);
                }
            }
        });

        bindSingleClicks(new int[]{
                R.id.tv_back, R.id.tv_record
        });

        getPresenter().getDownloadFileList();
    }

    /**
     * 绑定服务方式启动服务
     * */
    private void bindService(UrlPositionBean urlPosition){
        if(downloadServiceConnection != null){
            showMessage(new ToastMessageException("下载服务正在启动请稍后再试！"));
            return;
        }
        intent = new Intent(this, DownloadService.class);
        startService(intent);
        downloadServiceConnection = new UrlServiceConnection(urlPosition);
        bindService(intent, downloadServiceConnection, BIND_AUTO_CREATE);
    }

    private void stopService(){
        if(intent == null || downloadServiceConnection == null){
            return;
        }
        unbindService(downloadServiceConnection);
        stopService(intent);
        intent = null;
    }

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.tv_back) {
            finish();
        } else if (id == R.id.tv_record) {

        }
    }

    @Override
    public void notifyDownloadAdapterData(List<FileDownloadBean> list) {
        fileDownloadAdapter.refreshData();
        fileDownloadAdapter.setData(list);
    }
}
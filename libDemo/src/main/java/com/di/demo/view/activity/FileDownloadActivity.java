package com.di.demo.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.di.base.frame.mvp.base.ActivityPresenterView;
import com.di.base.log.DLog;
import com.di.demo.DownloadListener;
import com.di.demo.DownloadService;
import com.di.demo.R;
import com.di.demo.contract.FileDownloadContract;
import com.di.demo.model.FileDownloadModel;
import com.di.demo.presenter.FileDownloadPresenter;

import java.util.Locale;

public class FileDownloadActivity extends ActivityPresenterView<FileDownloadPresenter> implements FileDownloadContract.View{

    private TextView tvDownload;

    /**
     * 下载服务binder
     * */
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection downloadServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DLog.e("onServiceConnected");
            downloadBinder = (DownloadService.DownloadBinder) service;
            downloadBinder.setDownloadCallback(downloadListener);
            tvDownload.setEnabled(true);
            tvDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/bookResources/517b77c020064f5eb40e2c7a73e93244/audio.zip";
                    downloadBinder.startDownload(url);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            DLog.e("onServiceDisconnected");
        }
    };

    /**
     * 下载回调
     */
    private final DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onStart() {
            tvDownload.setText("开始下载...");
            tvDownload.setEnabled(false);
        }

        @Override
        public void onProgress(int progress) {
            tvDownload.setText(String.format(Locale.getDefault(), "下载中(%d%%)", progress));
            tvDownload.setEnabled(false);
        }

        @Override
        public void onPause() {
            tvDownload.setText("继续下载");
            tvDownload.setEnabled(true);
        }

        @Override
        public void onCancel() {
            tvDownload.setText("下载取消，重新下载");
            tvDownload.setEnabled(true);
        }

        @Override
        public void onSuccess() {
            tvDownload.setText("下载成功");
            tvDownload.setEnabled(true);
        }

        @Override
        public void onFail() {
            tvDownload.setText("下载失败，重新下载");
            tvDownload.setEnabled(true);
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
        tvDownload = get(R.id.tv_download);
        tvDownload.setText("下载");
        bindService();
    }

    /**
     * 绑定服务方式启动服务
     * */
    private void bindService(){
        intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, downloadServiceConnection, BIND_AUTO_CREATE);
    }

    private void stopService(){
        if(intent == null || downloadServiceConnection == null){
            return;
        }
        unbindService(downloadServiceConnection);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();
    }
}
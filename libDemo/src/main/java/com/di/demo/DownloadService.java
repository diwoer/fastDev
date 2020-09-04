package com.di.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * 文件下载服务
 * 1、使用AsyncTask处理下载过程
 * 2、单一任务下载
 * 3、使用服务下载，保证下载过程后台运行
 * 4、提供回调接口，刷新界面；提供下载方法，控制下载过程；
 * 5、根据url解析文件名称
 * 6、保存路径需要适配不同android版本
 *
 * 进阶
 * 1、断点续传
 * 2、提供暂停方法
 * 3、下载完成可以查看文件保存路径
 * 4、支持多任务同时下载
 * 5、支持等待状态
 * 6、动态申请文件权限
 * 7、适配android P 本地文件管理
 * 8、后台服务保活
 * */

public class DownloadService extends Service {

    private DownloadTask downloadTask;

    private DownloadListener downloadListener;

    final DownloadBinder binder = new DownloadBinder();

    final DownloadListener defaultDownloadListener = new DownloadListener() {

        @Override
        public void onStart() {

        }

        @Override
        public void onProgress(int progress) {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onFail() {

        }
    };



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 自定义Binder，用于交互
     */
    public class DownloadBinder extends Binder implements IDealDownload{

        /**
         * 设置下载回调参数
         * */
        public void setDownloadCallback(DownloadListener downloadListener){
            DownloadService.this.downloadListener = downloadListener;
        }

        @Override
        public void startDownload(String url) {

            if(downloadListener == null){
                downloadListener = defaultDownloadListener;
            }

            downloadTask = new DownloadTask(downloadListener);
            downloadTask.execute(url);
        }

        @Override
        public void pauseDownload() {
            downloadTask.onPause();
        }

        @Override
        public void cancelDownload() {
            downloadTask.cancel(true);
        }
    }

    interface IDealDownload {

        /**
         * 开始下载
         * */
        void startDownload(String url);

        /**
         * 停止下载
         * */
        void pauseDownload();

        /**
         * 取消下载
         * */
        void cancelDownload();
    }
}

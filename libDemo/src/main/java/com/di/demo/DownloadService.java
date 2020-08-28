package com.di.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class DownloadService extends Service {

    final DownloadBinder binder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 自定义Binder，用于交互
     */
    class DownloadBinder extends Binder implements IDealDownload{


        @Override
        public void startDownload(String url) {

        }

        @Override
        public void pauseDownload() {

        }

        @Override
        public void cancelDownload() {

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

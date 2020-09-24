package com.di.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * 主线程处理子线程消息
 */
public class ThreadHandler extends Handler {

    private DownloadListener listener;

    public ThreadHandler(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        Bundle bundle = msg.getData();
        switch (msg.what) {
            case DownloadStatus.ERROR:
                if(bundle != null){
                    listener.onError((DLError) bundle.getParcelable(IDLMessageCreator.ERROR));
                }
                break;
            case DownloadStatus.PROGRESS:
                if(bundle != null){
                    listener.onProgress((DLProgress) bundle.getParcelable(IDLMessageCreator.PROGRESS));
                }
                break;
            case DownloadStatus.SUCCESS:
                if(bundle != null){
                    listener.onSuccess((DLSuccess) bundle.getParcelable(IDLMessageCreator.SUCCESS));
                }
                break;
        }
    }
}

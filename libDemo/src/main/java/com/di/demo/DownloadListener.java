package com.di.demo;

public interface DownloadListener {

    /**
     * 下载之前需要配置的东西
     * 不要进行耗时操作，会阻塞线程
     * */
    void setBeforeStart();

    void onProgress(int progress);

    void onPause();

    void onCancel();

    void onSuccess(DLSuccess success);

    void onError(DLError error);
}

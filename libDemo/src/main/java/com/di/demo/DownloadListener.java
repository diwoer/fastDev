package com.di.demo;

public interface DownloadListener {

    void onStart();

    void onProgress(int progress);

    void onPause();

    void onCancel();

    void onSuccess();

    void onFail();
}

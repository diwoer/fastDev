package com.di.demo;

public interface DownloadListener {

    void onStart();

    void onProgress(int progress);

    void onPause();

    void onCancel();

    void onDestroy();

    void onSuccess();

    void onFail();
}

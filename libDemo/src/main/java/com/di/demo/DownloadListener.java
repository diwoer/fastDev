package com.di.demo;

import com.di.demo.data.bean.UrlPositionBean;

public interface DownloadListener {

    void onProgress(DLProgress dlProgress);

    void onPause(UrlPositionBean urlPosition);

    void onCancel(UrlPositionBean urlPosition);

    void onSuccess(DLSuccess success);

    void onError(DLError error);
}

package com.di.demo;

import android.os.Bundle;
import android.os.Message;

import com.di.demo.data.bean.UrlPositionBean;

public class ProgressMessageCreator implements IDLMessageCreator {

    private long progress;
    private long totalLength;
    private UrlPositionBean urlPosition;

    public ProgressMessageCreator(long progress, long totalLength, UrlPositionBean urlPosition){
        this.progress = progress;
        this.totalLength = totalLength;
        this.urlPosition = urlPosition;
    }

    @Override
    public Message create() {
        Message message = new Message();
        message.what = DownloadStatus.PROGRESS;
        Bundle bundle = new Bundle();
        DLProgress dlProgress = new DLProgress(progress, totalLength, urlPosition, (int) (progress * 100 / totalLength));
        bundle.putParcelable(PROGRESS, dlProgress);
        message.setData(bundle);
        return message;
    }
}

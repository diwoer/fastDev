package com.di.demo;

import android.os.Bundle;
import android.os.Message;

public class ProgressMessageCreator implements IDLMessageCreator {

    private long progress;
    private long totalLength;

    public ProgressMessageCreator(long progress, long totalLength){
        this.progress = progress;
        this.totalLength = totalLength;
    }

    @Override
    public Message create() {
        Message message = new Message();
        message.what = DownloadStatus.PROGRESS;
        Bundle bundle = new Bundle();
        bundle.putInt(PROGRESS, (int) (progress * 100 / totalLength));
        message.setData(bundle);
        return message;
    }
}

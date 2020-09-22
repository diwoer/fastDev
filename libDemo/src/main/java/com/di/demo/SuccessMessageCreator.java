package com.di.demo;

import android.os.Bundle;
import android.os.Message;

public class SuccessMessageCreator implements IDLMessageCreator {

    private String path;

    public SuccessMessageCreator(String path){
        this.path = path;
    }

    @Override
    public Message create() {
        Message message = new Message();
        message.what = DownloadStatus.SUCCESS;
        Bundle bundle = new Bundle();
        DLSuccess success = new DLSuccess();
        success.path = path;
        success.code = DLSuccess.DL_SUCCESS_CODE_DOWNLOAD;
        bundle.putSerializable(SUCCESS, success);
        message.setData(bundle);
        return message;
    }
}

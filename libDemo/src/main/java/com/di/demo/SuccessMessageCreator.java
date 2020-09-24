package com.di.demo;

import android.os.Bundle;
import android.os.Message;

import com.di.demo.data.bean.UrlPositionBean;

public class SuccessMessageCreator implements IDLMessageCreator {

    private String path;
    private UrlPositionBean urlPosition;

    public SuccessMessageCreator(String path, UrlPositionBean urlPosition){
        this.path = path;
        this.urlPosition = urlPosition;
    }

    @Override
    public Message create() {
        Message message = new Message();
        message.what = DownloadStatus.SUCCESS;
        Bundle bundle = new Bundle();
        DLSuccess success = new DLSuccess();
        success.path = path;
        success.code = DLSuccess.DL_SUCCESS_CODE_DOWNLOAD;
        success.urlPosition = urlPosition;
        bundle.putParcelable(SUCCESS, success);
        message.setData(bundle);
        return message;
    }
}

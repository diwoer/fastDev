package com.di.demo;

import android.os.Bundle;
import android.os.Message;

import com.di.demo.data.bean.UrlPositionBean;

public class ErrorMessageCreator implements IDLMessageCreator {

    private DLError dlError;

    public ErrorMessageCreator(DLError error){
        dlError = error;
    }

    @Override
    public Message create() {
        Message message = new Message();
        message.what = DownloadStatus.ERROR;
        Bundle bundle = new Bundle();
        bundle.putParcelable(ERROR, dlError);
        message.setData(bundle);
        return message;
    }
}

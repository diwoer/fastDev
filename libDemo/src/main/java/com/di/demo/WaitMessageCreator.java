package com.di.demo;

import android.os.Bundle;
import android.os.Message;

import com.di.demo.data.bean.UrlPositionBean;

public class WaitMessageCreator implements IDLMessageCreator {

    private UrlPositionBean urlPosition;

    public WaitMessageCreator(UrlPositionBean urlPosition){
        this.urlPosition = urlPosition;
    }

    @Override
    public Message create() {
        Message message = new Message();
        message.what = DownloadStatus.WAIT;
        Bundle bundle = new Bundle();
        bundle.putParcelable(WAIT, urlPosition);
        message.setData(bundle);
        return message;
    }
}

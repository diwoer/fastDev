package com.di.base.frame.bean;

import androidx.annotation.Nullable;

public class ToastMessageException extends Throwable {

    private String customMsg;

    public ToastMessageException(String msg){
        this.customMsg = msg;
    }

    @Nullable
    @Override
    public String getMessage() {
        return customMsg;
    }
}

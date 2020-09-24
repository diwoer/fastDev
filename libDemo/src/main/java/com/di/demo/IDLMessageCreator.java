package com.di.demo;

import android.os.Message;

public interface IDLMessageCreator {

    String PROGRESS = "progress";
    String ERROR = "error";
    String SUCCESS = "success";
    String WAIT = "wait";

    /**
     * 生成消息
     * */
    Message create();
}

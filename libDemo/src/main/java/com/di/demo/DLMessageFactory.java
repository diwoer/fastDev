package com.di.demo;

import android.os.Message;

/**
 * 生成下载消息
 * */
public class DLMessageFactory {

    public static Message create(IDLMessageCreator creator){
        return creator.create();
    }
}

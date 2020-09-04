package com.zjmy.rxbus;

/**
 * RxBus 传递的消息
 * */
public class RxMessage {

    //标识码
    public int code;

    //自定义数据
    public Object data;

    public RxMessage(int code){
        this.code = code;
    }
}

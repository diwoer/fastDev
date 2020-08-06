package com.di.module.eventbus;

/**
 * 传递的信息
 * */
public class EventMessage {

    /**
     * 信息的标识码
     * */
    public int code;

    /**
     * 数据模型
     *
     * 使用之前可以用 instanceof 判断一下
     * */
    public Object obj;

    public EventMessage(){}

    public EventMessage(int code){
        this.code = code;
    }

    public EventMessage(int code, Object obj){
        this.code = code;
        this.obj = obj;
    }
}

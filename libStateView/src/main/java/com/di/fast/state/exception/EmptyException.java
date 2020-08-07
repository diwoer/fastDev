package com.di.fast.state.exception;

/**
 * @作者 邸昌顺
 * @时间 2019/4/4 15:27
 * @描述
 */
public class EmptyException extends Throwable {

    public EmptyException(){
        super("暂无数据");
    }

    public EmptyException(String message){
        super(message);
    }

}

package com.di.base.net.response;

public class BaseResponse {

    /**
     * 后台接口状态码
     * */
    public int code = 200;

    /**
     * 接口返回的附带信息
     * 有可能是错误信息，有可能是提示信息
     * */
    public String message;

}

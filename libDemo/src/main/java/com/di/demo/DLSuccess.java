package com.di.demo;

import java.io.Serializable;

public class DLSuccess implements Serializable {

    /**
     * 下载文件路径
     */
    public String path;

    /**
     * 文件下载成功码
     * 区分是下载 还是 本地已经存在
     */
    public int code;

    /**
     * 下载
     */
    public static final int DL_SUCCESS_CODE_DOWNLOAD = 0;

    /**
     * 本地存在
     */
    public static final int DL_SUCCESS_CODE_EXIST = 1;

    public DLSuccess(){
        this.code = DL_SUCCESS_CODE_DOWNLOAD;
    }
}

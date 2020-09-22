package com.di.demo;

import java.io.Serializable;

public class DLError implements Serializable {

    /**
     * 通用错误码
     * */
    public static final int COMMON = 0;

    /**
     * 错误码
     * */
    public int code;

    /**
     * 错误描述
     * */
    public String description;

    public DLError(String description){
        this(COMMON, description);
    }

    public DLError(int code, String description){
        this.code = code;
        this.description = description;
    }
}

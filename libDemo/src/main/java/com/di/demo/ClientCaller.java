package com.di.demo;

import java.io.InputStream;

/**
 * 构建下载请求体
 * */
public interface ClientCaller {

    /**
     * 输入流形式返回数据
     * */
    InputStream streamResponse();

    /**
     * 文件总大小
     * */
    long totalLength();

}

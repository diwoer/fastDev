package com.di.demo.util;

import android.text.TextUtils;

import com.di.base.log.DLog;

import java.io.File;

public class FileUtil {

    public static String getFileNameFromUrl(String url){

        if(TextUtils.isEmpty(url)){
            return "null";
        }
        String[] urlNames = url.split("/");
        DLog.e("length=" + urlNames.length);
        if(urlNames.length > 1){
            return urlNames[urlNames.length-1];
        }
        return url;
    }

    /**
     * 可以通过连接获取一下文件保存的父目录
     * */
    public static String getFilePathFromUrl(String url){

        if(TextUtils.isEmpty(url)){
            return "null";
        }
        String[] urlNames = url.split("/");
        DLog.e("length=" + urlNames.length);
        if(urlNames.length > 2){
            return urlNames[urlNames.length-2] + File.separator + urlNames[urlNames.length-1];
        } else if(urlNames.length > 1){
            return urlNames[urlNames.length-1];
        }
        return url;
    }
}

package com.di.demo.util;

import android.text.TextUtils;

import com.di.base.log.DLog;

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
}

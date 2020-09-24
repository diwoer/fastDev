package com.di.demo.data.bean;

import com.di.demo.util.FileUtil;

public class FileDownloadBean {

    public String url;

    public String name;

    public FileDownloadBean(String url){
        this.url = url;
        this.name = FileUtil.getFileNameFromUrl(url);
    }
}

package com.di.demo;

import com.di.base.tool.ApplicationTool;
import com.di.demo.util.FileUtil;

import java.io.File;

public class FileCreator {

    private String url;

    public FileCreator(String url){
        this.url = url;
    }

    public File create(){
        String fileName = FileUtil.getFileNameFromUrl(url);
        String fileParentName = ApplicationTool.getInstance().getApplication().getApplicationContext().getExternalFilesDirs("Documents")[0] + File.separator;
        return new File(fileParentName, fileName);
    }
}

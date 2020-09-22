package com.di.demo;

import com.di.base.tool.ApplicationTool;
import com.di.demo.util.FileUtil;

import java.io.File;

public class FileCreator {

    private String url;
    private File file;

    public FileCreator(String url){
        this.url = url;
    }

    /**
     * 在本地创建要下载的文件
     * */
    public File create(){
        file = getDownloadLocalFile();
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取本地文件路径
     * */
    public String getFilePath(){
        return getDownloadLocalFile().getPath();
    }

    /**
     * 判断本地文件是否已经存在了
     * */
    public boolean exist(){
        return getDownloadLocalFile().exists();
    }

    /**
     * 获取本地文件
     * */
    private File getDownloadLocalFile(){
        if(file != null){
            return file;
        }
        String fileName = FileUtil.getFileNameFromUrl(url);
        String fileParentName = ApplicationTool.getInstance().getApplication().getApplicationContext().getExternalFilesDirs("Documents")[0] + File.separator;
        return new File(fileParentName, fileName);
    }
}

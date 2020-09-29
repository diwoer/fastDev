package com.di.file.data;

import java.io.File;

/**
 * 适配android 10以后的文件存储权限变更问题
 */
public class BaseRequest {

    /**
     * 相对路径，根据文件后缀名，放到对应位置
     * 如果不支持android R，则在sd卡根目录生成
     */
    public File file;

    /**
     * 文件类型{@link }
     */
    public String type;

    public BaseRequest(File file) {
        this.file = file;
        FileType.notifyFileType(this);
    }
}

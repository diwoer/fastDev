package com.di.file.fileaccess;

import android.content.Context;
import android.os.ParcelFileDescriptor;

import com.di.file.data.BaseRequest;
import com.di.file.response.FileResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface IFileAccess {

    /**
     * 打开文件
     * */
    <T extends BaseRequest> ParcelFileDescriptor openFile(Context context, T request) throws FileNotFoundException;

    /**
     * 创建文件
     * */
    <T extends BaseRequest> FileResponse createFile(Context context, T request);

    /**
     * 创建文件
     * */
    <T extends BaseRequest> FileResponse queryFile(Context context, T request);

    /**
     * 删除文件
     * */
    <T extends BaseRequest> boolean deleteFile(Context context, T request);

    /**
     * 重命名
     * */
    <T extends BaseRequest> FileResponse renameFile(Context context, T where, T request);

    /**
     * 创建文件
     * */
    <T extends BaseRequest> boolean mkdirs(Context context, T request);

    /**
     * 获取文件流
     * */
    <T extends BaseRequest> InputStream getInputStream(Context context, T request) throws FileNotFoundException;

    /**
     * 复制文件
     * */
    <T extends BaseRequest> boolean copyFile(Context context, T request);
}

package com.di.file.fileaccess;

import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;

import com.di.file.data.BaseRequest;
import com.di.file.data.FileRequest;
import com.di.file.data.ImageFileRequest;
import com.di.file.response.FileResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileStoreImpl implements IFileAccess {

    @Override
    public <T extends BaseRequest> ParcelFileDescriptor openFile(Context context, T request) throws FileNotFoundException {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse createFile(Context context, T request) {
        FileCreator fileCreator = getFileCreatorByBaseRequest(request);
        FileResponse response = new FileResponse();
        File file = new File(fileCreator.absoluteParentPath);
        boolean targetFileNotExist = true;//标识文件是否存在，因为exist()方法是耗时操作的，所以尽量减少使用
        if(!file.exists()){
            if(file.mkdirs()){
                targetFileNotExist = false;
            } else {
                return response;
            }
        }
        File targetFile = new File(fileCreator.absoluteParentPath, fileCreator.name);
        if(targetFileNotExist || !targetFile.exists()){
            try {
                if(targetFile.createNewFile()){
                    response.isSuccess = true;
                    response.file = targetFile;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public <T extends BaseRequest> FileResponse queryFile(Context context, T request) {
        FileCreator fileCreator = getFileCreatorByBaseRequest(request);
        FileResponse response = new FileResponse();
        File targetFile = new File(fileCreator.absoluteParentPath, fileCreator.name);
        if(targetFile.exists()){
            response.isSuccess = true;
            response.file = targetFile;
        }
        return response;
    }

    @Override
    public <T extends BaseRequest> boolean deleteFile(Context context, T request) {
        FileCreator fileCreator = getFileCreatorByBaseRequest(request);
        File targetFile = new File(fileCreator.absoluteParentPath, fileCreator.name);
        if(targetFile.exists()){
            return targetFile.delete();
        }
        return true;
    }

    @Override
    public <T extends BaseRequest> FileResponse renameFile(Context context, T where, T request) {
        FileCreator fileCreator = getFileCreatorByBaseRequest(where);
        FileResponse response = new FileResponse();
        File whereFile = new File(fileCreator.absoluteParentPath, fileCreator.name);
        if(whereFile.exists()){
            fileCreator = getFileCreatorByBaseRequest(request);
            File targetFile = new File(fileCreator.absoluteParentPath, fileCreator.name);
            response.isSuccess = whereFile.renameTo(targetFile);
            response.file = targetFile;
        }
        return response;
    }

    @Override
    public <T extends BaseRequest> boolean mkdirs(Context context, T request) {
        return false;
    }

    @Override
    public <T extends BaseRequest> InputStream getInputStream(Context context, T request) throws FileNotFoundException {
        return null;
    }

    @Override
    public <T extends BaseRequest> boolean copyFile(Context context, T request) {
        return false;
    }

    private static class FileCreator{

        //绝对路径
        public String absoluteParentPath;

        //文件名
        public String name;
    }

    private <T extends BaseRequest> FileCreator getFileCreatorByBaseRequest(T request){
        FileCreator fileCreator = new FileCreator();
        if(existSDCard()){
            if(request instanceof ImageFileRequest){
                ImageFileRequest imageRequest = (ImageFileRequest) request;
                String parentFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                Log.e("test", "parentFilePath = " + parentFilePath);
                fileCreator.absoluteParentPath += (parentFilePath + File.separator) ;
                if(!TextUtils.isEmpty(imageRequest.path)){
                    fileCreator.absoluteParentPath += (imageRequest.path + File.separator) ;
                }
                Log.e("test", "absolutePath = " + fileCreator.absoluteParentPath);
                fileCreator.name = imageRequest.displayName;
            } else if (request instanceof FileRequest){
                FileRequest fileRequest = (FileRequest) request;
                String parentFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                Log.e("test", "parentFilePath = " + parentFilePath);
                fileCreator.absoluteParentPath += (parentFilePath + File.separator) ;
                if(!TextUtils.isEmpty(fileRequest.path)){
                    fileCreator.absoluteParentPath += (fileRequest.path + File.separator) ;
                }
                Log.e("test", "absolutePath = " + fileCreator.absoluteParentPath);
                fileCreator.name = fileRequest.displayName;
            }
        } else {
            Log.e("test", "不存在sd卡");
        }
        return fileCreator;
    }

    private boolean existSDCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}

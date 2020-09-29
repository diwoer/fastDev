package com.di.file;

import android.os.Build;
import android.os.Environment;

import com.di.file.data.BaseRequest;
import com.di.file.fileaccess.FileStoreImpl;
import com.di.file.fileaccess.IFileAccess;
import com.di.file.fileaccess.MediaStoreAccessImpl;

public class FileAccessFactory {

    private MediaStoreAccessImpl mediaStoreAccess;
    private FileStoreImpl fileStoreAccess;

    private static volatile FileAccessFactory sInstance;

    private FileAccessFactory(){

    }

    public static FileAccessFactory getInstance(){
        if(sInstance == null){
            synchronized (FileAccessFactory.class){
                if(sInstance == null){
                    sInstance = new FileAccessFactory();
                }
            }
        }
        return sInstance;
    }

    public IFileAccess getFileAccess(BaseRequest request){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if(!Environment.isExternalStorageLegacy()){
                if(mediaStoreAccess == null){
                    mediaStoreAccess = new MediaStoreAccessImpl();
                }
                return mediaStoreAccess;
            }
        }
        if(fileStoreAccess == null){
            fileStoreAccess = new FileStoreImpl();
        }
        return fileStoreAccess;
    }
}

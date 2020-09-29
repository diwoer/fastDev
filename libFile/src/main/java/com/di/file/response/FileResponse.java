package com.di.file.response;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class FileResponse {

    public boolean isSuccess;
    public Uri uri;
    public File file;

    public String getPath(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(!Environment.isExternalStorageLegacy()){
                return uri.toString();
            }
        }
        return file.toString();
    }
}

package com.di.file.data;

import android.os.Environment;

import java.io.File;

public class FileRequest extends BaseRequest {

    public String displayName;
    public String path;
    public String title;

    public FileRequest(File file) {
        super(file);
    }

    public FileRequest(File file, String displayName, String path, String title) {
        super(file);
        this.displayName = displayName;
        this.path = path;
        this.title = title;
    }

    public String getPath() {
        return Environment.DIRECTORY_DOWNLOADS + "/"+path;
    }
}

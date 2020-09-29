package com.di.file.data;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class ImageFileRequest extends BaseRequest {

    public String mimeType;
    public String displayName;
    public String path;

    public ImageFileRequest(File file) {
        super(file);
    }

    public ImageFileRequest(File file, String mimeType, String displayName, String path) {
        super(file);
        this.mimeType = mimeType;
        this.displayName = displayName;
        this.path = path;
    }

    public String getPath() {
        if (!TextUtils.isEmpty(path)) {
            return Environment.DIRECTORY_PICTURES + "/" + path;
        }
        return null;
    }
}

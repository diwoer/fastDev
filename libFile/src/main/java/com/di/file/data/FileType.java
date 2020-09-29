package com.di.file.data;

import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.util.HashMap;

public class FileType {

    public static final String AUDIO = "Audio";
    public static final String VIDEO = "Video";
    public static final String IMAGE = "Pictures";
    public static final String DOWNLOADS = "Downloads";

    public static String[] ARR_AUDIO = new String[]{
            ".mp3", ".wav"
    };

    public static String[] ARR_VIDEO = new String[]{
            ".mp4", ".rmvb", ".avi"
    };

    public static String[] ARR_IMAGE = new String[]{
            ".jpg", ".png"
    };

    public static void notifyUriMap(HashMap<String, Uri> uriMap){
        uriMap.put(AUDIO, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        uriMap.put(VIDEO, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        uriMap.put(IMAGE, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            uriMap.put(DOWNLOADS, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
        }
    }

    public static void notifyFileType(BaseRequest request){
        if(request == null || request.file == null){
            throw new NullPointerException("please make sure the file not be null");
        }
        String absolutePath = request.file.getAbsolutePath();
        if(checkEndsByArr(absolutePath, ARR_AUDIO)){
            request.type = AUDIO;
        } else if (checkEndsByArr(absolutePath, ARR_VIDEO)){
            request.type = VIDEO;
        } else if (checkEndsByArr(absolutePath, ARR_IMAGE)){
            request.type = IMAGE;
        } else {
            request.type = DOWNLOADS;
        }
    }

    private static boolean checkEndsByArr(String path, String[] filterArr){
        for(String end : filterArr){
            if(path.endsWith(end)){
                return true;
            }
        }
        return false;
    }
}

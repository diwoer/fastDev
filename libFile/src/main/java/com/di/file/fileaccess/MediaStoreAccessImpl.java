package com.di.file.fileaccess;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import com.di.file.data.BaseRequest;
import com.di.file.data.FileRequest;
import com.di.file.data.FileType;
import com.di.file.data.ImageFileRequest;
import com.di.file.response.FileResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaStoreAccessImpl implements IFileAccess {

    private HashMap<String, Uri> uriMap;

    public MediaStoreAccessImpl(){
        uriMap = new HashMap<>();
        FileType.notifyUriMap(uriMap);
    }

    @Override
    public <T extends BaseRequest> ParcelFileDescriptor openFile(Context context, T request) throws FileNotFoundException {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse createFile(Context context, T request) {
        Uri uri = uriMap.get(request.type);
        preConditionCheckUri(uri);
        ContentValues contentValues = getContentValues(request);
        Uri resultUri = context.getContentResolver().insert(uri, contentValues);
        FileResponse response = new FileResponse();
        if(resultUri != null){
            response.isSuccess = true;
            response.uri = resultUri;
        }
        return response;
    }

    @Override
    public <T extends BaseRequest> FileResponse queryFile(Context context, T request) {
        Uri uri = uriMap.get(request.type);
        preConditionCheckUri(uri);
        ContentValues contentValues = getContentValues(request);
        Condition condition = new Condition(contentValues);
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        Cursor cursor = context.getContentResolver().query(uri, projection, condition.whereCause, condition.whereArgs, null);
        FileResponse response = new FileResponse();
        if(cursor != null && cursor.moveToFirst()){
            Uri resultUri = ContentUris.withAppendedId(uri, cursor.getLong(0));
            cursor.close();
            response.isSuccess = true;
            response.uri = resultUri;
        }
        return response;
    }

    @Override
    public <T extends BaseRequest> boolean deleteFile(Context context, T request) {
        FileResponse response = queryFile(context, request);
        if(response.isSuccess){
            Uri uri = queryFile(context, request).uri;
            context.getContentResolver().delete(uri, null, null);
            return true;
        }
        return false;
    }

    @Override
    public <T extends BaseRequest> FileResponse renameFile(Context context, T where, T request) {
        FileResponse response = queryFile(context, where);
        if(response.isSuccess){
            Uri uri = response.uri;
            ContentValues contentValues = getContentValues(request);
            int code = context.getContentResolver().update(uri, contentValues, null, null);
            response.isSuccess = code > 0;
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

    private <T extends BaseRequest> ContentValues getContentValues(T t){
        ContentValues contentValues = new ContentValues();
        if(t instanceof ImageFileRequest){
            ImageFileRequest imageRequest = (ImageFileRequest) t;
            if(imageRequest.mimeType != null){
                contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, imageRequest.mimeType);
            }
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, imageRequest.displayName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if(imageRequest.getPath() != null){
                    contentValues.put(MediaStore.Downloads.RELATIVE_PATH, imageRequest.getPath());
                }
            }
        } else if (t instanceof FileRequest){
            FileRequest fileRequest = (FileRequest) t;
            contentValues.put(MediaStore.Downloads.TITLE, fileRequest.title);
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileRequest.displayName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if(fileRequest.getPath() != null){
                    contentValues.put(MediaStore.Downloads.RELATIVE_PATH, fileRequest.getPath());
                }
            }
        }
        return contentValues;
    }

    private static class Condition{

        public String whereCause;
        private String[] whereArgs;

        public Condition(ContentValues contentValues){
            List<String> values = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1=1");
            for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                String key = entry.getKey();
                String value = (String) entry.getValue();
                if (value != null) {
                    stringBuilder.append(" and ").append(key).append(" =? ");
                    values.add(value);
                }
            }
            whereArgs = values.toArray(new String[values.size()]);
            this.whereCause = stringBuilder.toString();

            Log.e("test", "whereArgs.length = " + whereArgs.length);
        }
    }

    private void preConditionCheckUri(Uri uri){
        if(uri == null){
            throw new NullPointerException("make sure uri not be null");
        }
    }
}

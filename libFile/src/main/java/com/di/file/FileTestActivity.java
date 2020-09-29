package com.di.file;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.di.file.data.FileRequest;
import com.di.file.data.ImageFileRequest;
import com.di.file.response.FileResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class FileTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);
        checkPermission(this);
    }

    private static boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

        }
        return false;
    }

    public void createFile(View view) {
        final String name = "demo/";
        FileRequest request = new FileRequest(new File(name), name, name, name);
        FileResponse fileResponse = FileAccessFactory.getInstance()
                .getFileAccess(request)
                .createFile(this, request);
        if (fileResponse.isSuccess) {
            Toast.makeText(this, "创建文件夹成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void createImg(View view) {
        ImageFileRequest imageRequest = new ImageFileRequest(new File("demo.png"), "image/png", "demo.png", "Images");
        FileResponse fileResponse = FileAccessFactory.getInstance().getFileAccess(imageRequest).createFile(this, imageRequest);
        if(!fileResponse.isSuccess){
            Toast.makeText(this, "创建图片失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        try {
            OutputStream outputStream = getContentResolver().openOutputStream(fileResponse.uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            if (outputStream != null) {
                outputStream.close();
            } else {
                Toast.makeText(this, "添加图片失败", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "添加图片失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "添加图片成功", Toast.LENGTH_SHORT).show();
    }

    public void queryFile(View view) {
        ImageFileRequest imageRequest = new ImageFileRequest(new File("demo.png"));
        imageRequest.displayName = "demo.png";
        FileResponse fileResponse = FileAccessFactory.getInstance().getFileAccess(imageRequest).queryFile(this, imageRequest);
        if(fileResponse.isSuccess){
            Toast.makeText(this, "查询到文件: " + fileResponse.getPath(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFile(View view) {
        ImageFileRequest where = new ImageFileRequest(new File("demo.png"));
        where.displayName = "demo.png";
        ImageFileRequest imageRequest = new ImageFileRequest(new File("david.png"));
        imageRequest.displayName = "David.png";
        FileResponse fileResponse = FileAccessFactory.getInstance().getFileAccess(where).renameFile(this, where, imageRequest);
        if(fileResponse.isSuccess){
            Toast.makeText(this, "更新文件成功啦！", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteFile(View view) {
        ImageFileRequest imageRequest = new ImageFileRequest(new File("David.png"));
        imageRequest.displayName = "David.png";
        boolean isSuccess = FileAccessFactory.getInstance().getFileAccess(imageRequest).deleteFile(this, imageRequest);
        if(isSuccess){
            Toast.makeText(this, "删除文件成功啦！", Toast.LENGTH_SHORT).show();
        }
    }
}
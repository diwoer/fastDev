package com.di.demo;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Integer doInBackground(String... urls) {
        return singleDownload(urls[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    private int singleDownload(String url){

        try {

            /**
             * 配置文件保存位置和名字
             * */
            String fileName = "";
            String fileParentName = "";
            File file = new File(fileParentName, fileName);

            /**
             * 构建下载请求
             * */
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            long totalLength = response.body().contentLength();
            InputStream is = response.body().byteStream();
            FileOutputStream fos = new FileOutputStream(file);

            /**
             * 文件流拷贝文件，网络文件拷贝到本地
             * */
            byte[] buffer = new byte[1024];
            int len;
            long currentProgress = 0;
            while ((len = is.read(buffer)) != -1){
                fos.write(buffer, 0, len);
                currentProgress += len;
                publishProgress((int) (currentProgress * 100/totalLength));
            }
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return DownloadStatus.SUCCESS;
    }


}

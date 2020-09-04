package com.di.demo;

import android.os.AsyncTask;

import com.di.base.tool.ApplicationTool;
import com.di.demo.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public DownloadListener downloadListener;

    private final AtomicBoolean mPauseByUser = new AtomicBoolean();

    public DownloadTask(DownloadListener listener){
        this.downloadListener = listener;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        return singleDownload(urls[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case DownloadStatus.SUCCESS:
                downloadListener.onSuccess();
                break;
            case DownloadStatus.PAUSE:
                downloadListener.onPause();
                break;
            case DownloadStatus.ERROR:
                downloadListener.onFail();
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        downloadListener.onProgress(values[0]);
    }

    @Override
    protected void onCancelled(Integer integer) {
        downloadListener.onCancel();
    }

    public void onPause(){
        mPauseByUser.set(true);
    }

    private int singleDownload(String url){

        try {

            /**
             * 配置文件保存位置和名字
             * */
            String fileName = FileUtil.getFileNameFromUrl(url);
            String fileParentName = ApplicationTool.getInstance().getApplication().getApplicationContext().getExternalFilesDirs("Documents")[0] + File.separator;
            File file = new File(fileParentName, fileName);
            if(file.exists()){
                return DownloadStatus.SUCCESS_EXIST;
            }

            /**
             * 构建下载请求
             * */
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();

            /**
             * 暂停请求数据
             * */
            if(mPauseByUser.get()){
                return DownloadStatus.PAUSE;
            }

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
                /**
                 * 暂停拷贝数据
                 * */
                if(mPauseByUser.get()){
                    return DownloadStatus.PAUSE;
                }
                fos.write(buffer, 0, len);
                currentProgress += len;
                publishProgress((int) (currentProgress * 100/totalLength));
            }
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
            return DownloadStatus.ERROR;
        }

        return DownloadStatus.SUCCESS;
    }
}

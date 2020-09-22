package com.di.demo;

import android.os.AsyncTask;

import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public DownloadListener downloadListener;

    private final AtomicBoolean mPauseByUser = new AtomicBoolean();

    public DownloadTask(DownloadListener listener) {
        this.downloadListener = listener;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        return singleDownload(urls[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case DownloadStatus.SUCCESS:
                downloadListener.onSuccess(new DLSuccess());
                break;
            case DownloadStatus.PAUSE:
                downloadListener.onPause();
                break;
            case DownloadStatus.ERROR:
                downloadListener.onError(new DLError("错误"));
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

    public void onPause() {
        mPauseByUser.set(true);
    }

    private int singleDownload(String url) {
        FileCopier fileCopier = null;
        try {
            FileCopier.CopyProgressListener copyProgressListener = new FileCopier.CopyProgressListener() {
                @Override
                public void onProgress(long progress, long totalLength) {
                    publishProgress((int) (progress * 100 / totalLength));
                }
            };
            DLBuilder dlBuilder = new DLBuilder(url).createFile().clientCallServer().copyFile(copyProgressListener);
            fileCopier = dlBuilder.getFileCopier();
            dlBuilder.build();
            return DownloadStatus.SUCCESS;
        } catch (Exception e) {
            return DownloadStatus.ERROR;
        } finally {
            if (fileCopier != null) {
                fileCopier.closeIO();
            }
        }
    }
}

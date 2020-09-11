package com.di.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 用于拷贝数据
 */
public class FileCopier {

    private CopyProgressListener copyProgressListener;

    private long totalLength;

    private InputStream inputStream;
    private OutputStream outputStream;

    public FileCopier(long totalLength) {
        this.totalLength = totalLength;
    }

    /**
     * 拷贝进度监听
     */
    public interface CopyProgressListener {

        /**
         * 进度
         */
        void onProgress(long progress, long totalLength);
    }


    public void setCopyProgressListener(CopyProgressListener listener) {
        this.copyProgressListener = listener;
    }

    /**
     * 通过流的形式完成文件拷贝
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        byte[] buffer = new byte[1024];
        int len;
        long currentProgress = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
            currentProgress += len;
            publishProgress(currentProgress);
        }
        outputStream.flush();
    }

    private void publishProgress(long currentProgress) {
        if (copyProgressListener != null) {
            copyProgressListener.onProgress(currentProgress, totalLength);
        }
    }

    public void closeIO() {
        try {
            inputStream.close();
            outputStream.close();
            inputStream = null;
            outputStream = null;
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

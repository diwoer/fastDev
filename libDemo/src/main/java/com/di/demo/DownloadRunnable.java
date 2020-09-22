package com.di.demo;

import com.di.base.log.DLog;

public class DownloadRunnable implements Runnable {

    private String url;
    private ThreadHandler threadHandler;

    public DownloadRunnable(String url, ThreadHandler threadHandler) {
        this.url = url;
        this.threadHandler = threadHandler;
    }

    @Override
    public void run() {
        FileCopier fileCopier = null;
        try {
            FileCopier.CopyProgressListener copyProgressListener = new FileCopier.CopyProgressListener() {
                @Override
                public void onProgress(long progress, long totalLength) {
                    threadHandler.sendMessage(DLMessageFactory.create(new ProgressMessageCreator(progress, totalLength)));
                }
            };
            DLBuilder dlBuilder = new DLBuilder(url).createFile().clientCallServer().copyFile(copyProgressListener);
            fileCopier = dlBuilder.getFileCopier();
            dlBuilder.build();
            threadHandler.sendMessage(DLMessageFactory.create(new SuccessMessageCreator(dlBuilder.getFileCreator().getFilePath())));
        } catch (Exception e) {
            DLog.e(e.toString());
            threadHandler.sendMessage(DLMessageFactory.create(new ErrorMessageCreator(new DLError(e.getMessage()))));
        } finally {
            if (fileCopier != null) {
                fileCopier.closeIO();
            }
        }
    }
}

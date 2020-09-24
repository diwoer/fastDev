package com.di.demo;

import com.di.base.log.DLog;
import com.di.demo.data.bean.UrlPositionBean;

public class DownloadRunnable implements Runnable {

    private UrlPositionBean urlPosition;
    private ThreadHandler threadHandler;

    public DownloadRunnable(UrlPositionBean urlPosition, ThreadHandler threadHandler) {
        this.urlPosition = urlPosition;
        this.threadHandler = threadHandler;

        //每个新建任务开始状态都是等待中
        threadHandler.sendMessage(DLMessageFactory.create(new WaitMessageCreator(urlPosition)));
    }

    @Override
    public void run() {
        FileCopier fileCopier = null;
        try {
            FileCopier.CopyProgressListener copyProgressListener = new FileCopier.CopyProgressListener() {
                @Override
                public void onProgress(long progress, long totalLength) {
                    threadHandler.sendMessage(DLMessageFactory.create(new ProgressMessageCreator(progress, totalLength, urlPosition)));
                }
            };
            DLBuilder dlBuilder = new DLBuilder(urlPosition).createFile().clientCallServer().copyFile(copyProgressListener);
            fileCopier = dlBuilder.getFileCopier();
            dlBuilder.build();
            threadHandler.sendMessage(DLMessageFactory.create(new SuccessMessageCreator(dlBuilder.getFileCreator().getFilePath(), urlPosition)));
        } catch (Exception e) {
            DLog.e("DownloadRunnable: " + e.toString());
            threadHandler.sendMessage(DLMessageFactory.create(new ErrorMessageCreator(new DLError(e.getMessage(), urlPosition))));
        } finally {
            if (fileCopier != null) {
                fileCopier.closeIO();
            }
        }
    }

    /**
     * 获取线程处理Handler
     * */
    public ThreadHandler getThreadHandler() {
        return threadHandler;
    }
}

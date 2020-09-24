package com.di.demo.presenter;

import android.app.Activity;

import com.di.base.frame.mvp.BasePresenter;
import com.di.demo.contract.FileDownloadRecordContract;

public class FileDownloadRecordPresenter extends BasePresenter<FileDownloadRecordContract.Model, FileDownloadRecordContract.View> {

    public FileDownloadRecordPresenter(FileDownloadRecordContract.Model model, FileDownloadRecordContract.View view, Activity activity) {
        super(model, view, activity);
    }

}

package com.di.demo.presenter;

import android.app.Activity;

import com.di.base.frame.mvp.BasePresenter;
import com.di.demo.contract.FileDownloadContract;

public class FileDownloadPresenter extends BasePresenter<FileDownloadContract.Model, FileDownloadContract.View> {

    public FileDownloadPresenter(FileDownloadContract.Model model, FileDownloadContract.View view, Activity activity) {
        super(model, view, activity);
    }
}

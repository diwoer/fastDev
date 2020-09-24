package com.di.demo.view.activity;

import android.os.Bundle;

import com.di.base.frame.mvp.base.ActivityPresenterView;
import com.di.demo.R;
import com.di.demo.contract.FileDownloadRecordContract;
import com.di.demo.model.FileDownloadRecordModel;
import com.di.demo.presenter.FileDownloadRecordPresenter;

public class FileDownloadRecordActivity extends ActivityPresenterView<FileDownloadRecordPresenter> implements FileDownloadRecordContract.View {

    @Override
    protected FileDownloadRecordPresenter getPresenter() {
        return new FileDownloadRecordPresenter(new FileDownloadRecordModel(), this, this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(Throwable e) {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_file_download_record;
    }

    @Override
    public void outCreate(Bundle savedInstanceState) {

    }
}
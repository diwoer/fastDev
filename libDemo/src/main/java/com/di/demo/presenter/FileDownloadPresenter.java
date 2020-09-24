package com.di.demo.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.di.base.frame.bean.ToastMessageException;
import com.di.base.frame.mvp.BasePresenter;
import com.di.base.log.DLog;
import com.di.demo.data.response.ResponseDownloadFiles;
import com.di.base.net.usable.consumer.DataConsumer;
import com.di.base.net.usable.consumer.ExceptionConsumer;
import com.di.demo.contract.FileDownloadContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FileDownloadPresenter extends BasePresenter<FileDownloadContract.Model, FileDownloadContract.View> {

    public FileDownloadPresenter(FileDownloadContract.Model model, FileDownloadContract.View view, Activity activity) {
        super(model, view, activity);
    }

    public void getDownloadFileList(){
        addDispose(getDownloadFilesDisposable());
    }

    private Disposable getDownloadFilesDisposable() {
        return mModel.getDownloadFiles()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new DataConsumer<ResponseDownloadFiles>() {
                    @Override
                    public void onSuccess(ResponseDownloadFiles response) {

                        DLog.e("FileDownloadPresenter getDownloadFilesDisposable onSuccess");

                        final String commonErrorMsg = "数据为空";
                        if (response != null) {
                            if (response.code == 200) {
                                if(response.data != null){
                                    mView.notifyDownloadAdapterData(response.data.list);
                                } else {
                                    mView.showMessage(new ToastMessageException(commonErrorMsg));
                                }
                            } else if (!TextUtils.isEmpty(response.message)) {
                                mView.showMessage(new ToastMessageException(response.message));
                            } else {
                                mView.showMessage(new ToastMessageException(commonErrorMsg));
                            }
                        } else {
                            mView.showMessage(new ToastMessageException(commonErrorMsg));
                        }
                        mView.hideLoading();
                    }
                }, new ExceptionConsumer() {
                    @Override
                    public void onError(Throwable e) {
                        DLog.e("FileDownloadPresenter getDownloadFilesDisposable onError " + e.toString());
                        mView.showMessage(e);
                    }
                });
    }
}

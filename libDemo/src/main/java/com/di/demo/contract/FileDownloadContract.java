package com.di.demo.contract;

import com.di.base.frame.mvp.IModel;
import com.di.base.frame.mvp.IView;
import com.di.demo.data.bean.FileDownloadBean;
import com.di.demo.data.response.ResponseDownloadFiles;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface FileDownloadContract {

    interface View extends IView {

        void notifyDownloadAdapterData(List<FileDownloadBean> list);
    }

    interface Model extends IModel {

        Observable<ResponseDownloadFiles> getDownloadFiles();
    }
}

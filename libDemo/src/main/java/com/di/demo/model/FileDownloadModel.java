package com.di.demo.model;

import com.di.base.frame.mvp.BaseModel;
import com.di.base.log.DLog;
import com.di.demo.contract.FileDownloadContract;
import com.di.demo.data.bean.FileDownloadBean;
import com.di.demo.data.response.ResponseDownloadFiles;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class FileDownloadModel extends BaseModel implements FileDownloadContract.Model {

    @Override
    public Observable<ResponseDownloadFiles> getDownloadFiles() {

        return Observable.create(new ObservableOnSubscribe<ResponseDownloadFiles>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ResponseDownloadFiles> emitter) throws Throwable {

                DLog.e("FileDownloadModel");

                List<String> urls = new ArrayList<String>(){
                    {
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/0a250d6dd2264128b93239e0bb20d57d/question.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/0a250d6dd2264128b93239e0bb20d57d/rule.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/bookResources/ccf965b3be7a4d76b5c40beb513257ad/audio.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/bookResources/ccf965b3be7a4d76b5c40beb513257ad/ebook.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/88d82c99735c40839bc31eef7a2ae408/question.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/88d82c99735c40839bc31eef7a2ae408/rule.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/bookResources/ccf965b3be7a4d76b5c40beb513257ad/audio.zip");
                        add("http://192.168.10.166:9010/rest/upload/onlineshow/companyResources/bookResources/ccf965b3be7a4d76b5c40beb513257ad/ebook.zip");
                    }
                };

                ResponseDownloadFiles response = new ResponseDownloadFiles();
                response.data = new ResponseDownloadFiles.DATA();
                response.data.list = new ArrayList<>();
                FileDownloadBean bean;
                for(String url : urls){
                    bean = new FileDownloadBean(url);
                    response.data.list.add(bean);
                }
                emitter.onNext(response);

            }
        });
    }
}

package com.di.demo.data.response;

import com.di.base.net.response.BaseResponse;
import com.di.demo.data.bean.FileDownloadBean;

import java.util.ArrayList;
import java.util.List;

public class ResponseDownloadFiles extends BaseResponse {

    public DATA data;

    public static class DATA{

        public List<FileDownloadBean> list = new ArrayList<>();
    }
}

package com.di.demo;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 涉及到了同步网络请求，这个类需要在异步线程中操作
 * */
public class OkHttpClientCaller implements ClientCaller {

    private Response response;

    public OkHttpClientCaller(String url){

        /**
         * 构建okHttp请求数据，
         * */
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream streamResponse() {
        Preconditions.checkNull(response.body(), "[DLOkHttpClientCall] [streamResponse] 获取到的下载文件对象是空的");
        return response.body().byteStream();
    }

    @Override
    public long totalLength() {
        Preconditions.checkNull(response.body(), "[DLOkHttpClientCall] [totalLength] 获取到的下载文件对象是空的");
        return response.body().contentLength();
    }
}

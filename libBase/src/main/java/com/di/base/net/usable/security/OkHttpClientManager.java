package com.di.base.net.usable.security;

import com.di.base.log.DLog;
import com.di.base.net.usable.interceptor.HeaderAddInterceptor;
import com.di.base.net.usable.interceptor.HttpLoggingInterceptor;

import okhttp3.OkHttpClient;

public class OkHttpClientManager {

    private static volatile OkHttpClientManager sInstance = null;

    private OkHttpClientManager(){

    }

    public static OkHttpClientManager getInstance(){
        if(sInstance == null){
            synchronized (OkHttpClientManager.class){
                if(sInstance == null){
                    sInstance = new OkHttpClientManager();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient getOkHttpClient(){

        //添加 日志打印 拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                DLog.v("OkHttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //信任所有证书
        okHttpBuilder = SSLManager.getInstance().OkHttpSupportAllCerts(okHttpBuilder);

        return okHttpBuilder
                .cookieJar(LocalCookieJar.getInstance())
                .addNetworkInterceptor(new HeaderAddInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

}

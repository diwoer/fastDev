package com.di.base.net.usable;

import com.di.base.config.IPConfig;
import com.di.base.log.DLog;
import com.di.base.net.usable.interceptor.HeaderAddInterceptor;
import com.di.base.net.usable.interceptor.HttpLoggingInterceptor;
import com.di.base.net.usable.security.LocalCookieJar;
import com.di.base.net.usable.security.SSLSocketClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitManager {

    private CommonService commonService;

    private static volatile RetrofitManager sRetrofitManager = null;

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        if (sRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (sRetrofitManager == null) {
                    sRetrofitManager = new RetrofitManager();
                }
            }
        }
        return sRetrofitManager;
    }

    public CommonService getCommonService() {
        if (commonService == null) {
            commonService = getRetrofit().create(CommonService.class);
        }
        return commonService;
    }

    /**
     * retrofit 初始化
     * */
    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(IPConfig.COMMON_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    /**
     * okHttp 初始化
     * */
    private OkHttpClient getOkHttpClient() {

        //添加 日志打印 拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                DLog.v("OkHttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .cookieJar(LocalCookieJar.getInstance())
                .addNetworkInterceptor(new HeaderAddInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

}

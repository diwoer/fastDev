package com.di.base.net.usable;

import com.di.base.config.IPConfig;
import com.di.base.log.DLog;
import com.di.base.net.usable.interceptor.HeaderAddInterceptor;
import com.di.base.net.usable.interceptor.HttpLoggingInterceptor;
import com.di.base.net.usable.security.LocalCookieJar;
import com.di.base.net.usable.security.OkHttpClientManager;
import com.di.base.net.usable.security.SSLSocketClient;

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
                .client(OkHttpClientManager.getInstance().getOkHttpClient())
                .build();
    }

}

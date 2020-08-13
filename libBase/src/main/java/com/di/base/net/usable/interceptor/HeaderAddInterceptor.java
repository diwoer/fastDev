package com.di.base.net.usable.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderAddInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        int maxAge = 5;
        Request.Builder builder = chain.request().newBuilder();
        builder.url(chain.request().url())
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "public, max-age=" + maxAge)
                .addHeader("Connection", "close")
                .addHeader("authorization", "")
                .build();
        return chain.proceed(builder.build());
    }
}

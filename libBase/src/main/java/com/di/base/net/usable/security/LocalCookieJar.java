package com.di.base.net.usable.security;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class LocalCookieJar implements CookieJar {
    private static LocalCookieJar cookieJar;
    private List<Cookie> cookies;

    public static LocalCookieJar getInstance() {
        if (cookieJar == null) {
            cookieJar = new LocalCookieJar();
        }
        return cookieJar;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl arg0) {
//        if (cookies != null) {
//            return cookies;
//        }else {
//            //从本地保存的数据中获取
//            List<Cookie> cookies = SharedPreferencesUtil.getCookies();
//            if(cookies != null && cookies.size() > 0){
//                return cookies;
//            }
//        }
//        return new ArrayList<>();
        if (cookies != null) {
            return cookies;
        }
        return new ArrayList<>();
    }

    @Override
    public void saveFromResponse(HttpUrl arg0, List<Cookie> cookie) {
        cookies = cookie;
//        SharedPreferencesUtil.saveCookies(cookies);
    }

}

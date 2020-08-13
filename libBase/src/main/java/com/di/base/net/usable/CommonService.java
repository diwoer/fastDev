package com.di.base.net.usable;

import com.di.base.net.request.RequestLogin;
import com.di.base.net.response.ResponseLogin;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CommonService {

    @POST("/rest/sxreaderapp/login")
    Observable<ResponseLogin> login(@Body RequestLogin requestLogin);

}

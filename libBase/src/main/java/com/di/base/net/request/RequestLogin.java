package com.di.base.net.request;

public class RequestLogin {

    private String password;
    private String username;

    public RequestLogin(String password, String username) {
        this.password = password;
        this.username = username;
    }
}

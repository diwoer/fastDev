package com.di.frame.mvp;

import android.content.Intent;

public interface IView {

    void showLoading();

    void hideLoading();

    void showMessage();

    void transAct(Intent intent);

    void finishSelf();
}

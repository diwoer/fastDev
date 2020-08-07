package com.di.base.frame.mvp;

import android.os.Bundle;

public interface IView {

    void showLoading();

    void hideLoading();

    void showMessage(Throwable e);

    void trans(Class<?> cls, Bundle bundle);

    void transForResult(Class<?> cls, Bundle bundle, int requestCode);

    void finishAct();
}

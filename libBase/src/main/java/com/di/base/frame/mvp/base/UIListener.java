package com.di.base.frame.mvp.base;

import android.os.Bundle;

public interface UIListener {

    int getRootView();

    void outCreate(Bundle savedInstanceState);

    void outDestroy();
}

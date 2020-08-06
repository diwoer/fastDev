package com.di.base.absviewlistener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @作者 邸昌顺
 * @时间 2019/6/13 19:45
 * @描述 起到简化作用，在使用的时候，不用实现用不到的方法
 */
public abstract class TextWatcherImpl implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

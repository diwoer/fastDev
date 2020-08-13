package com.di.base.net.usable.consumer;

import com.di.base.log.DLog;

import io.reactivex.rxjava3.functions.Consumer;

public abstract class ExceptionConsumer implements Consumer<Throwable> {

    @Override
    public final void accept(Throwable e) throws Throwable {
        DLog.e("OkHttp_Error", e.toString());
        onError(e);
    }

    /**
     * 向下抛出方法，方便之后流程处理和调用
     * */
    public abstract void onError(Throwable e);

}

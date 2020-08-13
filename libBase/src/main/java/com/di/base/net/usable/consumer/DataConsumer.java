package com.di.base.net.usable.consumer;

import io.reactivex.rxjava3.functions.Consumer;

public abstract class DataConsumer<T> implements Consumer<T> {

    @Override
    public final void accept(T response) throws Throwable {
        onSuccess(response);
    }

    /**
     * 向下抛出方法，方便之后流程处理和调用
     * */
    public abstract void onSuccess(T response);

}

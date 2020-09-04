package com.zjmy.rxbus;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 使用Rx模式
 * 建立事件订阅-发布总线
 * */
public class RxBus {

    private static volatile RxBus sInstance = null;

    private Subject subject;

    private Map<Class<?>, Subscription> subscriptionMap;

    private RxBus(){
        subject = new SerializedSubject<>(PublishSubject.create());
        subscriptionMap = new HashMap<>();
    }

    public static RxBus getInstance(){
        if(sInstance == null){
            synchronized (RxBus.class){
                if(sInstance == null){
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    public void lookSubInfo(){
        Log.e("test", "RxBus " + subscriptionMap.size());
    }

    /**
     * 发送
     * */
    public void post(RxMessage message){
        subject.onNext(message);
    }

    /**
     * 获取观察者
     * */
    private <T> Observable<T> getObservable(Class<T> eventType){
        return subject.ofType(eventType);
    }

    //注册事件
    public void register(Object obj, OnEventListener listener){
        Subscription subscription = getObservable(RxMessage.class).subscribe(new Action1<RxMessage>() {
            @Override
            public void call(RxMessage message) {
                listener.onEvent(message);
            }
        });

        subscriptionMap.put(obj.getClass(), subscription);
    }

    //取消注册
    public void unRegister(Object obj){
        if(subscriptionMap != null && subscriptionMap.containsKey(obj.getClass())){
            Subscription subscription = subscriptionMap.get(obj.getClass());
            if(subscription != null && !subscription.isUnsubscribed()){
                subscription.unsubscribe();
            }
            subscriptionMap.remove(obj.getClass());
        }
        logSubscriptionMap();
    }

    //清空所有事件
    public void onDestroy(){
        if(subscriptionMap != null){
            for(Subscription subscription : subscriptionMap.values()){
                if(!subscription.isUnsubscribed()){
                    subscription.unsubscribe();
                }
            }
            subscriptionMap.clear();
        }
    }

    private void logSubscriptionMap(){
        Log.e("test", "logSubscriptionMap");
        if(subscriptionMap != null){
            for(Class<?> key : subscriptionMap.keySet()){
                Log.e("test", "key " + key.getName());
            }
        }
    }

}

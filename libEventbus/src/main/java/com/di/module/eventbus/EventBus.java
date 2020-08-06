package com.di.module.eventbus;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {

    private static final String TAG = "EventBus";

    private static volatile EventBus sInstance = null;

    private SubscriberMethodFinder mSubscriberMethodFinder;

    private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new HashMap<>();

    private final Map<Class<?>, CopyOnWriteArrayList<Subscription>> mSubscriptionsByEventType;
    private final Map<Object, List<Class<?>>> mTypesBySubscriber;

    private final ThreadLocal<PostingThreadState> mCurrentPostingThreadState = new ThreadLocal<PostingThreadState>() {
        @Override
        protected PostingThreadState initialValue() {
            return new PostingThreadState();
        }
    };

    private EventBus() {
        mSubscriberMethodFinder = new SubscriberMethodFinder();
        mSubscriptionsByEventType = new HashMap<>();
        mTypesBySubscriber = new HashMap<>();
    }

    public static EventBus getDefault() {
        if (sInstance == null) {
            synchronized (EventBus.class) {
                if (sInstance == null) {
                    sInstance = new EventBus();
                }
            }
        }
        return sInstance;
    }

    public void register(Object subscriber) {
        Class<?> subscriberClass = subscriber.getClass();
        List<SubscriberMethod> subscriberMethods = mSubscriberMethodFinder.findSubscriberMethods(subscriberClass);
        synchronized (this) {
            for (SubscriberMethod subscriberMethod : subscriberMethods) {
                subscribe(subscriber, subscriberMethod);
            }
        }
    }

    public void unRegister(Object subscriber) {
        List<Class<?>> subscribedTypes = mTypesBySubscriber.get(subscriber);
        if (subscribedTypes != null) {
            for (Class<?> eventType : subscribedTypes) {
                unsubscribeByEventType(subscriber, eventType);
            }
            mTypesBySubscriber.remove(subscriber);
        } else {
            Log.e(TAG, "Subscriber to unregister was not registered before: " + subscriber.getClass());
        }
    }

    public void post(EventMessage event) {
        PostingThreadState postingState = mCurrentPostingThreadState.get();
        List<Object> eventQueue = postingState.eventQueue;
        eventQueue.add(event);

        if (!postingState.isPosting) {
            postingState.isMainThread = true;
            postingState.isPosting = true;
            if (postingState.canceled) {
                throw new EventBusException("Internal error. Abort state was not reset");
            }
            try {
                while (!eventQueue.isEmpty()) {
                    postSingleEvent(eventQueue.remove(0), postingState);
                }
            } finally {
                postingState.isPosting = false;
                postingState.isMainThread = false;
            }
        }
    }

    public static void clearCaches() {
        SubscriberMethodFinder.clearCaches();
        eventTypesCache.clear();
    }

    private void unsubscribeByEventType(Object subscriber, Class<?> eventType) {
        List<Subscription> subscriptions = mSubscriptionsByEventType.get(eventType);
        if (subscriptions != null) {
            int size = subscriptions.size();
            for (int i = 0; i < size; i++) {
                Subscription subscription = subscriptions.get(i);
                if (subscription.subscriber == subscriber) {
                    subscription.active = false;
                    subscriptions.remove(i);
                    i--;
                    size--;
                }
            }
        }
    }

    private void postSingleEvent(Object event, PostingThreadState postingState) throws Error {
        Class<?> eventClass = event.getClass();
        boolean subscriptionFound = false;
        List<Class<?>> eventTypes = lookupAllEventTypes(eventClass);
        int countTypes = eventTypes.size();
        for (int h = 0; h < countTypes; h++) {
            Class<?> clazz = eventTypes.get(h);
            subscriptionFound |= postSingleEventForEventType(event, postingState, clazz);
        }
        if (!subscriptionFound) {
            Log.e(TAG, "No subscribers registered for event " + eventClass);
        }
    }

    private boolean postSingleEventForEventType(Object event, PostingThreadState postingState, Class<?> eventClass) {
        CopyOnWriteArrayList<Subscription> subscriptions;
        synchronized (this) {
            subscriptions = mSubscriptionsByEventType.get(eventClass);
        }
        if (subscriptions != null && !subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                postingState.event = event;
                postingState.subscription = subscription;
                boolean aborted;
                try {
                    invokeSubscriber(subscription, event);
                    aborted = postingState.canceled;
                } finally {
                    postingState.event = null;
                    postingState.subscription = null;
                    postingState.canceled = false;
                }
                if (aborted) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    private void invokeSubscriber(Subscription subscription, Object event) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "SubscriberExceptionEvent subscriber " + subscription.subscriber.getClass()
                    + " threw an exception " + e.getCause());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unexpected exception", e);
        }
    }

    private static List<Class<?>> lookupAllEventTypes(Class<?> eventClass) {
        synchronized (eventTypesCache) {
            List<Class<?>> eventTypes = eventTypesCache.get(eventClass);
            if (eventTypes == null) {
                eventTypes = new ArrayList<>();
                Class<?> clazz = eventClass;
                while (clazz != null) {
                    eventTypes.add(clazz);
                    addInterfaces(eventTypes, clazz.getInterfaces());
                    clazz = clazz.getSuperclass();
                }
                eventTypesCache.put(eventClass, eventTypes);
            }
            return eventTypes;
        }
    }

    private static void addInterfaces(List<Class<?>> eventTypes, Class<?>[] interfaces) {
        for (Class<?> interfaceClass : interfaces) {
            if (!eventTypes.contains(interfaceClass)) {
                eventTypes.add(interfaceClass);
                addInterfaces(eventTypes, interfaceClass.getInterfaces());
            }
        }
    }

    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {

        Class<?> eventType = subscriberMethod.eventType;
        Subscription newSubscription = new Subscription(subscriber, subscriberMethod);
        CopyOnWriteArrayList<Subscription> subscriptions = mSubscriptionsByEventType.get(eventType);
        if (subscriptions == null) {
            subscriptions = new CopyOnWriteArrayList<>();
            mSubscriptionsByEventType.put(eventType, subscriptions);
        } else {
            if (subscriptions.contains(newSubscription)) {
                throw new EventBusException("Subscriber " + subscriber.getClass() + " already registered to event "
                        + eventType);
            }
        }
        subscriptions.add(newSubscription);

        List<Class<?>> subscribedEvents = mTypesBySubscriber.get(subscriber);
        if (subscribedEvents == null) {
            subscribedEvents = new ArrayList<>();
            mTypesBySubscriber.put(subscriber, subscribedEvents);
        }
        subscribedEvents.add(eventType);
    }

    private final static class PostingThreadState {
        final List<Object> eventQueue = new ArrayList<>();
        boolean isPosting;
        boolean isMainThread;
        Subscription subscription;
        Object event;
        boolean canceled;
    }
}

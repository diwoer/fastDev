package com.di.module.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriberMethodFinder {

    private static final int BRIDGE = 0x40;
    private static final int SYNTHETIC = 0x1000;

    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC | BRIDGE | SYNTHETIC;
    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();

    private static final int POOL_SIZE = 4;
    private static final FindState[] FIND_STATE_POOL = new FindState[POOL_SIZE];

    SubscriberMethodFinder() {

    }

    List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if (subscriberMethods != null) {
            return subscriberMethods;
        }

        subscriberMethods = findUsingInfo(subscriberClass);

        if (subscriberMethods.isEmpty()) {
            throw new EventBusException("Subscriber " + subscriberClass
                    + " and its super classes have no public methods with the @Subscribe annotation");
        } else {
            METHOD_CACHE.put(subscriberClass, subscriberMethods);
            return subscriberMethods;
        }
    }

    private List<SubscriberMethod> findUsingInfo(Class<?> subscriberClass) {
        FindState findState = prepareFindState();
        findState.initForSubscriber(subscriberClass);
        while (findState.clazz != null) {
            findUsingReflectionInSingleClass(findState);
            findState.moveToSuperclass();
        }
        return getMethodsAndRelease(findState);
    }

    private List<SubscriberMethod> getMethodsAndRelease(FindState findState) {
        List<SubscriberMethod> subscriberMethods = new ArrayList<>(findState.subscriberMethods);
        findState.recycle();
        synchronized (FIND_STATE_POOL) {
            for (int i = 0; i < POOL_SIZE; i++) {
                if (FIND_STATE_POOL[i] == null) {
                    FIND_STATE_POOL[i] = findState;
                    break;
                }
            }
        }
        return subscriberMethods;
    }

    private FindState prepareFindState() {
        synchronized (FIND_STATE_POOL) {
            for (int i = 0; i < POOL_SIZE; i++) {
                FindState state = FIND_STATE_POOL[i];
                if (state != null) {
                    FIND_STATE_POOL[i] = null;
                    return state;
                }
            }
        }
        return new FindState();
    }

    private void findUsingReflectionInSingleClass(FindState findState) {
        Method[] methods;
        try {
            methods = findState.clazz.getDeclaredMethods();
        } catch (Throwable th) {
            methods = findState.clazz.getMethods();
            findState.skipSuperClasses = true;
        }
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                    if (subscribeAnnotation != null) {
                        Class<?> eventType = parameterTypes[0];
                        if (findState.checkAdd(method, eventType)) {
                            findState.subscriberMethods.add(new SubscriberMethod(method, eventType));
                        }
                    }
                }
            }
        }
    }

    static void clearCaches() {
        METHOD_CACHE.clear();
    }

    static class FindState {
        final List<SubscriberMethod> subscriberMethods = new ArrayList<>();
        final Map<Class, Object> anyMethodByEventType = new HashMap<>();
        final Map<String, Class> subscriberClassByMethodKey = new HashMap<>();
        final StringBuilder methodKeyBuilder = new StringBuilder(128);

        Class<?> subscriberClass;
        Class<?> clazz;
        boolean skipSuperClasses;

        void initForSubscriber(Class<?> subscriberClass) {
            this.subscriberClass = clazz = subscriberClass;
            skipSuperClasses = false;
        }

        void recycle() {
            subscriberMethods.clear();
            anyMethodByEventType.clear();
            subscriberClassByMethodKey.clear();
            methodKeyBuilder.setLength(0);
            subscriberClass = null;
            clazz = null;
            skipSuperClasses = false;
        }

        boolean checkAdd(Method method, Class<?> eventType) {
            // 2 level check: 1st level with event type only (fast), 2nd level with complete signature when required.
            // Usually a subscriber doesn't have methods listening to the same event type.
            Object existing = anyMethodByEventType.put(eventType, method);
            if (existing == null) {
                return true;
            } else {
                if (existing instanceof Method) {
                    if (!checkAddWithMethodSignature((Method) existing, eventType)) {
                        // Paranoia check
                        throw new IllegalStateException();
                    }
                    // Put any non-Method object to "consume" the existing Method
                    anyMethodByEventType.put(eventType, this);
                }
                return checkAddWithMethodSignature(method, eventType);
            }
        }

        private boolean checkAddWithMethodSignature(Method method, Class<?> eventType) {
            methodKeyBuilder.setLength(0);
            methodKeyBuilder.append(method.getName());
            methodKeyBuilder.append('>').append(eventType.getName());

            String methodKey = methodKeyBuilder.toString();
            Class<?> methodClass = method.getDeclaringClass();
            Class<?> methodClassOld = subscriberClassByMethodKey.put(methodKey, methodClass);
            if (methodClassOld == null || methodClassOld.isAssignableFrom(methodClass)) {
                // Only add if not already found in a sub class
                return true;
            } else {
                // Revert the put, old class is further down the class hierarchy
                subscriberClassByMethodKey.put(methodKey, methodClassOld);
                return false;
            }
        }

        void moveToSuperclass() {
            if (skipSuperClasses) {
                clazz = null;
            } else {
                clazz = clazz.getSuperclass();
                String clazzName = clazz.getName();
                /** Skip system classes, this just degrades performance. */
                if (clazzName.startsWith("java.") || clazzName.startsWith("javax.") || clazzName.startsWith("android.")) {
                    clazz = null;
                }
            }
        }
    }
}

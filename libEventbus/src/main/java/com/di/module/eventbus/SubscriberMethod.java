package com.di.module.eventbus;

import java.lang.reflect.Method;

public class SubscriberMethod {

    final Method method;
    final Class<?> eventType;
    /**
     * Used for efficient comparison
     */
    String methodString;

    public SubscriberMethod(Method method, Class<?> eventType) {
        this.method = method;
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof SubscriberMethod) {
            checkMethodString();
            SubscriberMethod otherSubscriberMethod = (SubscriberMethod) other;
            otherSubscriberMethod.checkMethodString();
            return methodString.equals(otherSubscriberMethod.methodString);
        } else {
            return false;
        }
    }

    private synchronized void checkMethodString() {
        if (methodString == null) {
            methodString = method.getDeclaringClass().getName() +
                    '#' + method.getName() +
                    '(' + eventType.getName();
        }
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }
}

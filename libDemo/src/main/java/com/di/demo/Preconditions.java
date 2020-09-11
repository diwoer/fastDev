package com.di.demo;

/**
 * 数据在使用之前的判断，统一收集问题，便于定位问题
 */
public final class Preconditions {

    /**
     * 检测对象是否为空，抛出异常
     *
     * @param objects  要检测的多个对象
     * @param throwMsg 对象为空时，需要抛出的提示语
     */
    public static void checkNull(Object[] objects, String throwMsg) {
        for (Object obj : objects) {
            if (obj == null) {
                throw new NullPointerException(throwMsg);
            }
        }
    }

    /**
     * 检测对象是否为空，抛出异常
     *
     * @param object   要检测的对象
     * @param throwMsg 对象为空时，需要抛出的提示语
     */
    public static void checkNull(Object object, String throwMsg) {
        if (object == null) {
            throw new NullPointerException(throwMsg);
        }
    }
}

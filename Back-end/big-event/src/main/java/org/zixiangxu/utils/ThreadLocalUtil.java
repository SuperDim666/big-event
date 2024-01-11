package org.zixiangxu.utils;/*
 * @author Zixiang Xu
 * @date 2024/1/1 09:20
 */


/**
 * ThreadLocal util class
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {

    // Provide ThreadLocal object
    // Note: globally unique
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // Obtain value from key
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // Save value into key
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }


    // Clear ThreadLocal to prevent memory leaks
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}

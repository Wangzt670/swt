package com.cqu.swt.common;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登陆用户id，进行公共字段填充
 */
public class BaseContext {
    private static ThreadLocal<Long> treadLocal = new ThreadLocal<>();

    /**
     * 设置当前用户id
     * @param id
     */
    public static void setCurrentId(Long id) {
        treadLocal.set(id);
    }

    /**
     * 获取当前用户id
     * @return
     */
    public static Long getCurrentId() {
        return treadLocal.get();
    }
}

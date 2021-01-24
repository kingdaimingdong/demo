package com.mina.npay.gateway.common.utils;

/**
 * daimingdong
 * 计时工具类
 */
public class ElapsedTimerUtils {

    /**
     * 开始时间
     */
    public static long startTime;

    /**
     * 结束时间
     */
    public static  long endTime;

    /**
     *  开始计时
     */
    public static void init(){
        startTime = System.currentTimeMillis();
    }

    /**
     * 用时时长
     * @return
     */
    public static String elapsedTime(){
        endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        return Float.toString(seconds);
    }
}

package com.mina.npay.gateway.common.utils;

import java.security.SecureRandom;

/**
 * 随机数工具类
 * Created by daimingdong
 */
public class RandomUtils {

    private static final Object OBJECT = new Object();
    private static long bIndex = 0;


    /**
     * 生成用户编号 默认生成9位数随机数,支持千万级用户，不支持分布式
     */
    public static String getUid(){
        return createSerialNo(9);
    }

    /**
     * 可用多线程检测是否会产生相同随机数，不支持分布式系统
     * @param length
     * @return
     */
    public static String createSerialNo(int length) {
        double max = Math.pow(10, length);
        String curSerial;
        synchronized (OBJECT) {
            if (++bIndex >= max){
                bIndex = 0;
            }
            curSerial = bIndex + "";
        }
        while (curSerial.length() < length) {
            curSerial = "0" + curSerial;
        }
        return curSerial;
    }

    /**
     * 生成6位随机验证码
     * @return
     */
    public static int getVerifyCode() {
        SecureRandom secRandom = new SecureRandom();
       return secRandom.nextInt(999999);
    }


    public static void main(String[] args){
        for(int i=0;i<10;i++) {
//            System.out.println(getVerifyCode());
            System.out.println(createSerialNo(4));
        }
    }
}

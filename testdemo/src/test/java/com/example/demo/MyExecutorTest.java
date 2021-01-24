package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutorTest {

    @Test
    public void cacheThreadPoolTest(){
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int ii = i;
            try {
                Thread.sleep(ii * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachedThreadPool.execute(()->System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii));
        }
    }
}

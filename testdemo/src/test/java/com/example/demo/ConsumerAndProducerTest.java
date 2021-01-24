package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerAndProducerTest {

    @Test
    public void mainTest() {

        BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

        ProducerThread mt1 = new ProducerThread("producer ",queue);    // 实例化对象
        ConsumerThread mt2 = new ConsumerThread("consumer ",queue);    // 实例化对象
        Thread t1 = new Thread(mt1);       // 实例化Thread类对象
        Thread t2 = new Thread(mt2);       // 实例化Thread类对象
        t1.start();    // 启动多线程
        t2.start();    // 启动多线程

        while(true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class ProducerThread implements Runnable { // 实现Runnable接口，作为线程的实现类
        private String name;       // 表示线程的名称
        private BlockingQueue<String> queue;

        public ProducerThread(String name,BlockingQueue<String> queue) {
            this.name = name;      // 通过构造方法配置name属性
            this.queue = queue;
        }

        public void run() {  // 覆写run()方法，作为线程 的操作主体
            while(true){
                try {
                    queue.put("hello world");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }

    class ConsumerThread implements Runnable { // 实现Runnable接口，作为线程的实现类
        private String name;       // 表示线程的名称
        private BlockingQueue<String> queue;

        public ConsumerThread(String name,BlockingQueue<String> queue) {
            this.name = name;      // 通过构造方法配置name属性
            this.queue = queue;
        }

        public void run() {  // 覆写run()方法，作为线程 的操作主体
            while(true){
                String key = null;
                try {
                    key = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(key);
            }
        }

    }
}

package com.fantj.jedis.test;

import com.fantj.jedis.client.Jedis;
import com.fantj.jedis.pool.JedisPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试连接池
 */
public class PoolTest {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool("www.fantj.top",6380);

        CountDownLatch cdl = new CountDownLatch(50);
        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    cdl.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Jedis jedis = null;
                try {
                    jedis= pool.getResource();
                    jedis.set("key"+count.get(),"value"+count.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    count.incrementAndGet();
                    pool.release(jedis);
                }
            }).start();
            cdl.countDown();
        }
    }
}

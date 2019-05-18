package com.fantj.jedis.pool;

import com.fantj.jedis.client.Jedis;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class JedisPool implements Pool<Jedis>{

    public JedisPool(String url, int port) {
        this.url = url;
        this.port = port;
        init(maxTotal,maxWaitMillis);
    }

    public JedisPool(String url, int port, int maxTotal, long maxWaitMillis) {
        this.url = url;
        this.port = port;
        this.maxTotal = maxTotal;
        this.maxWaitMillis = maxWaitMillis;
    }

    private String url;
    private int port;
    private int maxTotal = 20;
    private long maxWaitMillis = 1000;
    // 空闲的连接queue
    private LinkedBlockingQueue<Jedis> idleWorkQueue = null;
    private Queue<Jedis> activeWorkQueue = null;
    // 当前连接数量
    private AtomicInteger count = new AtomicInteger(0);
    @Override
    public void init(int maxTotal, long maxWaitMillis) {
        maxTotal = maxTotal;
        maxWaitMillis = maxWaitMillis;
        idleWorkQueue = new LinkedBlockingQueue<>(maxTotal);
        activeWorkQueue = new LinkedBlockingQueue<>(maxTotal);
    }

    @Override
    public Jedis getResource() throws Exception {
        Jedis jedis = null;
        // 1. 记录开始时间，检测超时
        long startTime = System.currentTimeMillis();
        while (true){
            // 2. 从空闲队列中获取连接，如果拿到，一式两份存放到活动队列
            jedis = idleWorkQueue.poll();
            if (jedis != null){
                activeWorkQueue.offer(jedis);
                return jedis;
            }
            // 3. 如果失败，判断池是否满，没满则创建
            if (count.get() < maxTotal){
                if (count.incrementAndGet() <= maxTotal){
                    jedis = new Jedis(url,port);
                    activeWorkQueue.offer(jedis);
                    System.out.printf("创建了一个新的连接: %s \r\n", jedis.toString());
                    return jedis;
                }else {
                    count.decrementAndGet();
                }
            }
            // 4. 如果连接池满了，则在超时时间内进行等待
            try {
                jedis = idleWorkQueue.poll(maxWaitMillis-(System.currentTimeMillis()-startTime), TimeUnit.MILLISECONDS);
                if (jedis != null){
                    activeWorkQueue.offer(jedis);
                    return jedis;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 5. poll可能被中断，所以在这里再进行超时判断
            if (maxWaitMillis < (System.currentTimeMillis()-startTime)){
                throw new RuntimeException("JedisPool: jedis connect timeout");
            }
        }
    }

    @Override
    public void release(Jedis jedis) {
        if (activeWorkQueue.remove(jedis)){
            idleWorkQueue.offer(jedis);
        }
    }
}

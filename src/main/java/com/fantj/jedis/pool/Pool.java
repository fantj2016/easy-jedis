package com.fantj.jedis.pool;

import com.fantj.jedis.client.Jedis;

/**
 * 连接池契约
 */
public interface Pool<T> {
    /**
     * 初始化连接池
     * @param maxTotal 最大连接数
     * @param maxWaitMillis 最大等待时间
     */
    public void init(int maxTotal, long maxWaitMillis);

    /**
     * 获取连接
     * @return 返回jedis对象
     */
    public Jedis getResource() throws Exception;

    /**
     * 释放连接
     */
    public void release(T t);
}

package com.fantj.jedis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * 客户端模拟
 * 在这里你将配合ServerMock 来抓取 Jedis 所发出去的请求列表 *3\r\n $6\r\n APPEND\r\n ...
 * 为什么要抓取这些信息，更方便我们在Protocol封装成 RESP 协议，才能够和server通信
 */
public class ClientMock {

    Jedis jedis = new Jedis("localhost",6379);
    @Test
    public void set(){
        jedis.set("hello","hello");
        String hello = jedis.get("fantj");
        System.out.println(hello);
    }


    @Test
    public void setNXEX(){
        jedis.set("fantj","fantj","NX","EX",1000);
        String hello = jedis.get("fantj");
        System.out.println(hello);
    }
    @Test
    public void append(){
        jedis.append("fantj","666");
        String hello = jedis.get("fantj");
        System.out.println(hello);
    }

}

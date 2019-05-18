package com.fantj.jedis.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

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

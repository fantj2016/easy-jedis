package com.fantj.jedis.test;

import com.fantj.jedis.client.Client;
import org.junit.Test;
import redis.clients.jedis.Jedis;


public class Main {
    private Client client = new Client("www.fantj.top",6380);
    @Test
    public void set(){
        client.set("fantj","fantj");
        String result = client.get("fantj");
        System.out.println(result);
    }
    @Test
    public void setNx(){
        client.set("fantj","fantj","NX","EX",10000);
        String result = client.get("fantj");
        System.out.println(result);
    }
    @Test
    public void append(){
//        client.append("fantj","-2019");
        String fantj = client.get("fantj");
        System.out.println(fantj);
    }
}

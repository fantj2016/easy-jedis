package com.fantj.jedis.client;

public class Jedis extends Client{

    public Jedis(String host, int port) {
        super(host, port);
    }

    @Override
    public String set(String key, String value) {
        return super.set(key, value);
    }

    @Override
    public String get(String key) {
        return super.get(key);
    }

    @Override
    public void set(String key, String value, String nx, String ex, int i) {
        super.set(key, value, nx, ex, i);
    }

    @Override
    public void append(String key, String value) {
        super.append(key, value);
    }
}

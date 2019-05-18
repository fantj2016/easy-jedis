package com.fantj.jedis.client;

import com.fantj.jedis.connect.Connection;
import com.fantj.jedis.protocol.Protocol;

public class Client {
    private Connection connection;
    public Client(String host, int port){
        connection = new Connection(host,port);
    }

    public String set(String key, String value) {
        connection.sendCommand(Protocol.Command.SET, key.getBytes(), value.getBytes());
        return connection.getStatus();
    }

    public String get(String key) {
        connection.sendCommand(Protocol.Command.GET, key.getBytes());
        return connection.getStatus();
    }


    public void set(String key, String value, String nx, String ex, int i) {
        connection.sendCommand(Protocol.Command.SET, key.getBytes(), value.getBytes(), nx.getBytes(), ex.getBytes(), String.valueOf(i).getBytes());
    }
    public void append(String key, String value){
        connection.sendCommand(Protocol.Command.APPEND, key.getBytes(),value.getBytes());
    }
}

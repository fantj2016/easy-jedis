package com.fantj.jedis.connect;

import com.fantj.jedis.protocol.Protocol;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 连接类
 * 在这里进行创建连接并处理IO请求，用inputStream进行数据回显，
 * 提供OutputStream给协议层，以便让其给服务端发送命令
 */
public class Connection {
    private String host = "localhost";
    private int port = 6379;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    public Connection() {
    }

    public Connection(String host) {
        this.host = host;
    }

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public Connection sendCommand(Protocol.Command cmd, byte[]... args) {
        connect();
        Protocol.sendCommand(outputStream, cmd, args);
        return this;
    }

    private void connect() {

        try {
            if (socket == null) {  //IO复用
                socket = new Socket(host, port);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 操作状态的返回
     * 比如：SET 操作成功返回 +OK
     */
    public String getStatus() {
        byte[] bytes = new byte[1024];
        try {
            socket.getInputStream().read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes);
    }
}

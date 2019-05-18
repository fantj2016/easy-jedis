package com.fantj.jedis.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务模拟
 * 模拟一个redis服务，开放一个本地的6379端口，接收jedis传来的数据并拼接出 RESP 协议
 * 和redis server 的通讯是需要 RESP 协议做支撑
 */
public class ServerMock {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        byte [] bytes = new byte[1024];
        inputStream.read(bytes);
        System.out.println(new String(bytes));
    }
}

package com.fantj.jedis.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

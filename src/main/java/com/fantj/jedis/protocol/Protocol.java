package com.fantj.jedis.protocol;

import java.io.IOException;
import java.io.OutputStream;

/**
 * RESP协议
 */
public class Protocol {
    public static final String DOLLAR_BYTE = "$";
    public static final String ASTERISK_BYTE = "*";
    public static final byte PLUS_BYTE = 43;
    public static final byte MINUS_BYTE = 45;
    public static final byte COLON_BYTE = 58;
    public static final String BLANK_BYTE = "\r\n";

    /**
     * 拼接RESP 并 发送write
     */
    public static void sendCommand(OutputStream os,Protocol.Command cmd, byte[] ... args){
        // 1. 生成协议 *3 $3 SET $3 key $5 value
        StringBuffer stringBuffer = new StringBuffer();
        // 1.1 数组长度 *3
        stringBuffer.append(ASTERISK_BYTE).append(args.length+1).append(BLANK_BYTE);
        // 1.2 命令长度 $3
        stringBuffer.append(DOLLAR_BYTE).append(cmd.name().length()).append(BLANK_BYTE);
        // 1.3 命令 SET / GET
        stringBuffer.append(cmd).append(BLANK_BYTE);
        for (byte[] arg: args){
            // 1.4 key/value 长度
            stringBuffer.append(DOLLAR_BYTE).append(arg.length).append(BLANK_BYTE);
            // 1.5 key/value
            stringBuffer.append(new String(arg)).append(BLANK_BYTE);
        }
        // 响应
        try {
            os.write(stringBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 定义一个枚举类 存放命令
     */
    public static  enum Command{
        SET , GET , KEYS,APPEND
    }}

## Easy-Jedis
>是一个Jedis的简单实现代码，方便理解Jedis工作原理和加强自身架构能力。

## How to use

```
    public void set(){
        Client client = new Client("www.fantj.top",6380);
        client.set("fantj","fantj");
        String result = client.get("fantj");
        System.out.println(result);
    }
```

## How to work?
>工作原理。

#### RESP协议：
>官方文献：
https://redis.io/topics/protocol

用于和redis通信的底层协议，全称为redis serialization protocol.特点：简单实现，解析很快，易读懂。

总之就是将数据拼装成这种协议，redis服务才能识别。

案例：
> 如何拿到这个案例。
```
*3   // 数据一共有三个数组
//数组1
$6   //下行为6个长度的字符串
APPEND   
//数组2
$5    // 下行为5个长度的字符串
fantj
//数组3
$3    // 下行为3个长度的字符串
666
```
然后我们进行拼接
```$xslt
StringBuffer sb = new StringBuffer();
// 注意每个类型表示完后都进行换行
sb.append("*").append("3").append("\r\n");
sb.append("$").append("6").append("\r\n");
sb.append("APPEND").append("\r\n");
sb.append("$").append("5").append("\r\n");
sb.append("fantj").append("\r\n");
sb.append("$").append("3").append("\r\n");
sb.append("666").append("\r\n");
// 然后用os进行写出
os.write(sb.toString().getBytes());
```
#### redis运行流程：
发送命令-命令排队-命令执行-返回结果


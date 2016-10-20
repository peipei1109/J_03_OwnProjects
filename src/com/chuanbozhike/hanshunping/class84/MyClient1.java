package com.chuanbozhike.hanshunping.class84;


import java.io.PrintWriter;
import java.net.*;
public class MyClient1 {
	
	public static void main(String[] args) {
		MyClient1 myClient1 =new MyClient1();
		
	}
	
	
	public MyClient1(){
		try {
			
			//socket函数就是去链接某一个服务器端：127.0.0.1 表示服务器的ip，9999就是端口号
			Socket s =new Socket("127.0.0.1", 9999);
			//如果socket链接成功，就可以发数据给服务器了
			//我们通过pwriter这个对象，向socket里面写数据了，true表示及时刷新
			PrintWriter pWriter=new PrintWriter(s.getOutputStream(),true);
			pWriter.println("你好吗？我是客户端~~~");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

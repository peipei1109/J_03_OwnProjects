package com.chuanbozhike.hanshunping.class84;
/**
 * 这是第一个服务器端程序，让它在9999端口监听
 * 它可以接收从客户端发来的信息
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class MyServer1 {
	public static void main(String[] args) {
		MyServer1 myServer1=new MyServer1();
	}

	
	public  MyServer1(){
		
		try {
			//在9999号端口监听
			ServerSocket ssServerSocket=new ServerSocket(9999);
			System.out.println("我是服务器，我在9999端口监听");
			//等待某个客户端来链接，但是具体哪一个客户端没关系，该函数会返回一个socket链接
			Socket s=ssServerSocket.accept();
			//System.out.println("11");
			
			//要读取s中传递的数据
			InputStreamReader isr=new InputStreamReader(s.getInputStream());
			BufferedReader br=new BufferedReader(isr);
			
			//读一行
		    String info =br.readLine();
		    
		    //打印一下
		    System.out.println("服务器接收到："+info);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}

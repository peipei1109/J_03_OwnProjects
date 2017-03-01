package com.interview;

import java.io.FileReader;
import java.io.Reader;
/*
 * 和字符串操作有关的
 */
public class stingUtil {
	
//	1、写一个函数，要求输入一个字符串和一个字符长度，对该字符串进行分隔。
	public String[]split(String str,int chars){  
		int n=(str.length()+chars-1)/chars;  
		String ret[]=new String[n];  
		for(int i=0;i<n;i++){  
		if(i<n-1){  
		ret[i]=str.substring(i*chars,(i+1)*chars);  
		}else{  
		ret[i]=str.substring(i*chars);  
		}  
		}  
		return ret;  
		}  

//	2、写一个函数，2个参数，1个字符串，1个字节数，返回截取的字符串，要求字符串中的中文不能出现乱码：如（“我ABC”，4）应该截为“我AB”，输 入（“我ABC汉DEF”，6）应该输出为“我ABC”而不是“我ABC+汉的半个”。
	public String subString(String str,int subBytes){  
		int bytes=0;//用来存储字符串的总字节数  
		for(int i=0;i<str.length();i++){  
		if(bytes==subBytes){  
		return str.substring(0,i);  
		}  
		char c=str.charAt(i);  
		if(c<256){  
		bytes+=1;//英文字符的字节数看作1  
		}else{  
		bytes+=2;//中文字符的字节数看作2  
		if(bytes-subBytes==1){  
		return str.substring(0,i);  
		}  
		}  
		}  
		return str;  
		}  
	
	
//	3、写一个方法,输入一个文件名和一个字符串,统计这个字符串在这个文件中出现的次数
	public int countWords(String file,String find)throws Exception  
	{  
	int count=0;  
	Reader in=new FileReader(file);  
	int c;  
	while((c=in.read())!=-1){  
	while(c==find.charAt(0)){  
	for(int i=1;i<find.length();i++){  
	c=in.read();  
	if(c!=find.charAt(i))break;  
	if(i==find.length()-1)count++;  
	}  
	}  
	}  
	return count;  
	}  
}

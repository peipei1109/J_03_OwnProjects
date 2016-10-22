package com.interview;

import java.awt.print.Printable;

public class TransJinZhi{
	public static void main(String[] args) 
    {
		
		TenTo2x trans1 =new TenTo2x();
		trans1.toHex(16);
		trans1.toba(60);
		trans1.toBin(60);
		
		new TenTo36().trans (18,17);
    }
}



class TenTo2x
{
	//有bug，只能转换成进制数为2幂次方的进制
    

//十进制转二进制
    public static void toBin (int num)
    {
        trans(num,1,1);
    }

//十进制转八进制
    public static void toba (int num)
    {
        trans(num,7,3);
    }

//十进制转十六进制
    public static void toHex (int num)
    {
        trans(num,15,4);
    }

//    具体转换过程。
//    方法中第一个参数为需要转换的数 ，第二参数为位运算&上的数，第三个参数为要移动的二进制位。
    public static void trans (int num,int base,int offset)
    {
//        定义一个数组，角标对应数值，可以看做一个查询表。
        char[] chs = {'0' , '1' , '2' , '3' , '4' , '5' ,
        		  '6' , '7' , '8' , '9' , 'a' , 'b' ,
        		  'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        		  'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        		  'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        		  'u' , 'v' , 'w' , 'x' , 'y' , 'z'};
        char[] arr = new char[32];
        int pos = arr.length;

        if(num==0)
        {
//            如果要转换的数为0，则无需转换。
            System.out.println(0);
        }

        while(num!=0)
        {
//            得出指定进制中每一位的数值，存入一个数组中。
            int temp = num & base;
            arr[--pos] = chs[temp];
//            每得出一个指定进制一个位上的值后，原数值的二进制位就往右移动指定位数，并在前面补零。
            num = num >>> offset;      //移位补零。
        }
        
        for(int x=pos;x<arr.length;x++)
        {
            if (x==(arr.length-1)) {
                System.out.println(arr[x]);                
            } else {
                System.out.print(arr[x]);    
            }
        }
    }
}


class TenTo36{
	
	
    public static void trans (int num,int base)
    {
//        定义一个数组，角标对应数值，可以看做一个查询表。
        char[] chs = {'0' , '1' , '2' , '3' , '4' , '5' ,
        		  '6' , '7' , '8' , '9' , 'a' , 'b' ,
        		  'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        		  'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        		  'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        		  'u' , 'v' , 'w' , 'x' , 'y' , 'z'};
        char[] arr = new char[32];
        int pos = arr.length;
        int rest=0;

        if(num==0)
        {
//            如果要转换的数为0，则无需转换。
            System.out.println(0);
        }

        while(num!=0)
        {
//            得出指定进制中每一位的数值，存入一个数组中。
            
            rest=num%base;
            num=num/base;
            
            arr[--pos] = chs[rest];
//            System.out.println("余数："+num+','+rest+','+arr[--pos]);
        }
        
        for(int x=pos;x<arr.length;x++)
        {
            if (arr.length-1==x) {
                System.out.println(arr[x]);                
            } else {
                System.out.print(arr[x]);    
            }
        }
    }
	
}

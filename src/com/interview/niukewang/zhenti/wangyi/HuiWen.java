package com.interview.niukewang.zhenti.wangyi;

import java.awt.HeadlessException;
import java.util.Scanner;


/*
 * 如果一个数字序列逆置之后跟原序列是一样的就称这样的数字序列为回文序列。例如：
{1, 2, 1}, {15, 78, 78, 15} , {112} 是回文序列, 
{1, 2, 2}, {15, 78, 87, 51} ,{112, 2, 11} 不是回文序列。
现在给出一个数字序列，允许使用一种转换操作：
选择任意两个相邻的数，然后从序列移除这两个数，并用这两个数字的和插入到这两个数之前的位置(只插入一个和)。
现在对于所给序列要求出最少需要多少次操作可以将其变成回文序列。

输入描述:
输入为两行，第一行为序列长度n ( 1 ≤ n ≤ 50)
第二行为序列中的n个整数item[i]  (1 ≤ iteam[i] ≤ 1000)，以空格分隔。


输出描述:
输出一个数，表示最少需要的转换次数

输入例子:
4
1 1 1 3

输出例子:
2
 */
public class HuiWen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub\

		Scanner scanner = new Scanner(System.in);
		int n = 0;
		int[] item = null;
		while (scanner.hasNext()) {
			n = scanner.nextInt();
			item = new int[n];
			for (int i = 0; i < n; i++) {
				item[i] = scanner.nextInt();
			}
			System.out.println(leastTimeToHuiwen(n, item));
		}

	}

	public static int leastTimeToHuiwen(int n, int[] item) {
		// TODO Auto-generated method stub

		int head = 0;
		int tail = n - 1;
		int head_times = 0;
		int tail_times = 0;
		while (tail > head) {
			if (item[head] > item[tail]) {
				item[--tail] += item[tail + 1];
				tail_times++;
			} else if (item[head] < item[tail]) {
				item[++head] += item[head - 1];
				head_times++;

			} else {
				head++;
				tail--;
			}

		}

//		// print huiwen,得到最后得到的回文
//		for (int j = head_times; j < n - tail_times; j++)
//			System.out.print(item[j]);
//		System.out.println();
		return head_times + tail_times;
	}

}

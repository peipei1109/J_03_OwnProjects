package com.interview.niukewang.zhenti.wangyi;

import java.awt.HeadlessException;
import java.util.Scanner;

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

		// print huiwen,得到最后得到的回文
		for (int j = head_times; j < n - tail_times; j++)
			System.out.print(item[j]);
		System.out.println();
		return head_times + tail_times;
	}

}

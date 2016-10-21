package com.interview.niukewang.zhenti.wangyi;

import java.util.Scanner;

public class EntityDots {

	public static void main(String[] args) {
		trans1(); // 未优化，只通过了80%

	}

	public static void trans1() {
		Scanner scanner = new Scanner(System.in);
		int r = scanner.nextInt();
		int count = 0;

		for (int i = 1; i <= Math.sqrt((float) r); i++) {
			for (int j = 1; j <= Math.sqrt((float) r); j++) {
				if (r == (i * i + j * j)) {
					count++;

				}
			}
		}
		count *= 4;
		int s = (int) Math.sqrt((float) r);
		if (r == s * s) {
			count += 4;
		}
		System.out.println(count);
	}

	public static void trans2() {
		Scanner in = new Scanner(System.in);
		int rSquare = in.nextInt();
		int count = 0;
		double r = Math.sqrt(rSquare);

		// 存储值
		for (int i = 0; i < r; i++) {
			/*
			 * 运行超时 for(int j=1;j<r+1;j++)
			 * { if(i*i+j*j==rSquare)
			 *  { count++; 
			 *  } 
			 * }
			 */
			// 优化点1
			double j = Math.sqrt(rSquare - i * i);
			if ((int) j == j) {
				count++;
			}
		}

		// 优化点2
		System.out.print(count << 2);

	}
}

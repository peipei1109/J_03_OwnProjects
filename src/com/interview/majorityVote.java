package com.interview;

public class majorityVote {
	
	public majorityVote(){
	}
	
	public static majorityVote mv=new majorityVote();

	public static int Find_Majority(int[] array) {
		int major = 0, count = 0;
		int i = 0;
		while (i < array.length) {
			if (i == 0) {
				major = array[0];
				count = 1;
			} else if (major == array[i]) { // 如果数组扫描到的数和当前majority数相等。
				count++;
			} else if (major != array[i] && count != 0) {// 如果数组扫描到的数和当前majority数不相等，且当前majority数的票数至少有一票。
				count--;
			} else {

				major = array[i];
			}
			i++;
		}
		int tmp_count = 0;
		for (int j = 0; j < array.length; j++) {
			if (array[j] == major)
				tmp_count++;
		}
		if (tmp_count >= (array.length + 1) / 2) // 检验majority数的票数是否超过了总票数的一半
			return major;
		else
			return -1;

	}
	
	
	public static void main(String[] args) {
		int[] array={1,2,3,4,5,6,6,6,6,6,6,6,6,6};
		int res=0;
		
		res=majorityVote.Find_Majority(array);
		System.out.println(res);
		
	}

}

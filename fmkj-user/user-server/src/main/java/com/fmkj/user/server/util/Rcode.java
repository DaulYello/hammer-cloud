package com.fmkj.user.server.util;

public class Rcode {
	static private String transform(int num, int n) {
		// 参数num为输入的十进制数，参数n为要转换的进制
		int array[] = new int[100];
		int location = 0;
		while (num != 0) {// 当输入的数不为0时循环执行求余和赋值
			int remainder = num % n;
			num = num / n;
			array[location] = remainder;// 将结果加入到数组中去
			location++;
		}
		return show(array, location - 1);

	}

	static private String show(int[] arr, int n) {

		char[] s = "0VBNhj234FGHM1gJK56tyuiopwerIOPAklzxYU78cvbnmQWERT9qSDLasdfZXC".toCharArray();
		String ret = "";
		for (int i = 5; i >= 0; i--) {
			ret = ret + s[arr[i]];
		}
		return ret;
	}

	static public String getRcode(int uid) {

		return transform(uid, 62);

	}
}

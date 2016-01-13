package com.ziroom.developer.string;

/**
 * 随机生成唯一码
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 上午11:23:29
 * @since 1.0.0
 */
public class RandomString {
	
	public static String getPassword(int size) {
		String s = "";
		for (int i = 0; i < size; i++) {
			if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9) {
				s += getRandomInt(9, 0);
			} else {
				s += getRandomChar();
			}
		}
		return s;

	}

	private static char getRandomChar() {
		int i = getRandomInt(122, 65);
		if (i > 90 && i < 97) {
			return getRandomChar();
		} else {
			return (char) i;
		}
	}

	private static int getRandomInt(int max, int min) {
		return (int) (Math.random() * (max + 1 - min) + min);
	}
	
	public static void main(String[] args) {
		System.out.println(RandomString.getPassword(16));

	}
}

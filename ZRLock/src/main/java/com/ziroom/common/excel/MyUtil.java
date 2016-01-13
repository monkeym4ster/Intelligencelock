package com.ziroom.common.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {

	/**
	 * 获取当前时间的字符串 格式为"yyyyMMddHHmmss"
	 * 
	 * @return 返回获取的当前时间
	 */
	public static String getCurrentTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String curDate = sdf.format(new Date());

		return curDate;
	}

}

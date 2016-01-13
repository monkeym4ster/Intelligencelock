package com.ziroom.common.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String DATE_FORMAT_STR = "yyMMdd";

	public static String sysDateToStr(String format) {
		return dateToStr(getCurrSysDate(), format);

	}

	/**
	 * 获取系统当前时间。取应用服务器上设定的时间。
	 * <p>
	 * <b>注意：</b><br>
	 * 
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * 
	 * </p>
	 * 
	 * @return 返回<code>Timestamp</code>类型的值。
	 * @author: guohm
	 * @date:2014年11月28日 下午4:05:02
	 * @since 1.0.0
	 */
	public static Timestamp getCurrSysDate() {
		Timestamp currTime = new Timestamp(System.currentTimeMillis());
		return currTime;
	}

	public static String dateToStr(Date date, String format) {
		String dateStr = null;
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			dateStr = simpleDateFormat.format(date);
		}
		return dateStr;
	}

}

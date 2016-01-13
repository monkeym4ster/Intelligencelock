package com.ziroom.common.constant;


/**
 * 
 * 系统常量类定义
 * 
 * @author: 张志宏
 * @email: it_javasun@yeah.net
 * @date:2015年8月12日 下午2:10:51
 * @since 1.0.0
 */
public interface SysConstant {
	/**
	 * 常用标识定义
	 */
	// 字符串标识定义
	public static final String FLAG_STR_0 = "0";
	public static final String FLAG_STR_1 = "1";
	public static final String FLAG_STR_2 = "2";
	public static final String FLAG_STR_3 = "3";
	public static final String FLAG_STR_4 = "4";
	public static final String FLAG_STR_5 = "5";

	// 整型标识定义
	public static final Integer FLAG_INT_0 = 0;
	public static final Integer FLAG_INT_1 = 1;
	public static final Integer FLAG_INT_2 = 2;
	public static final Integer FLAG_INT_3 = 3;
	public static final Integer FLAG_INT_4 = 4;
	public static final Integer FLAG_INT_5 = 5;

	// 字符编码定义
	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";
	public static final String GB2312 = "GB2312";
	public static final String ASCII = "ASCII";
	// 分隔符
	public static final String SPLIT_COMMA = ",";

	// 时间格式定义
	public static final String FORMAT_YMD_EN = "yyyy-MM-dd";
	public static final String FORMAT_YMDHMS_EN = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YMDHMSS_EN = "yyyy-MM-dd HH:mm:ss SSS";
	// 中国时间格式
	public static final String FORMAT_YMD_CN = "yyyy年MM月dd日";
	public static final String FORMAT_YMDHMS_CN = "yyyy年MM月dd日 HH时mm分ss秒";
	// SOLR时间格式
	public static final String FORMAT_SOLR = "yyyy-MM-dd'T'HH:00:00'Z'";
	// 时间格式
	public static final String TIME_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss SSS";

	// 开关常量定义
	public static final String OFF = "OFF"; // 关闭
	public static final String ON = "ON"; // 开启

	/************************ 数据源常量标识 ****************************/
	// 自如智能锁数据源标识
	public static final String FCDATASOURCE = "fcDataSource";
	// 北京资产数据源标识
	public static final String BJ_ZCDATASOURCE = "zcDataSource";
	// 银企直联
	public static final String BADDATASOURCE = "badDataSource";
	
	/************************ 智能锁系统Collections ****************************/
	public static final String COLLECTION_HOUSE = "house";
	
	/************************ 智能锁系统基础参数 ****************************/
	public static final int PAGE_SIZE = 10;
	public static final CharSequence ZAS = "ZAS";	// 自如内部员工
	public static final CharSequence ZLS = "ZLS";	// 智能锁系统用户
	

}

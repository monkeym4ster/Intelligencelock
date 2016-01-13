package com.ziroom.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 城市枚举
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月6日 上午10:28:43
 * @since 1.0.0
 */
public class CityEnum {
	/**
	 * 根据传入的ID获取城市编码
	 * @return
	 * @author: 张志宏 
	 * @date:2015年6月5日 下午5:36:06
	 * @since 1.0.0
	 */
	public static String getCityCodeValue(Object city_code) {
		return cityCodeMap().get(city_code);
	}
	
	/**
	 * 城市CODE
	 * @return
	 * @author: 张志宏 
	 * @date:2015年6月5日 下午5:37:39
	 * @since 1.0.0
	 */
	private static Map<String, String> cityCodeMap() {
		Map<String, String> map = new HashMap<String, String>();
		// 北京
		map.put("110000", "北京");
		// 上海
		map.put("310000", "上海");
		// 深圳
		map.put("440300", "深圳");
		return map;
	}
	
}

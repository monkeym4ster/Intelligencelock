package com.ziroom.common.config;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 读取config.properties的信息
 * 
 * @author: liuxm
 * @date:2015年2月2日 下午7:16:00
 * @since 1.0.0
 */
public class ConfigUtil {

	// 读取Properties文件信息
	public static Properties properties = null;

	// 读取Properties中文问件信息
	public static Properties propertiesCN = null;

	// 读取统计用Properties文件信息
	public static Properties propertiesInfo = null;

	public final static String FILE_NAME = "statistics.properties";

	public final static String ResultException = "errorMsg";

	public final static String Already_Exists = "已存在导入的收款";

	static {

		try {
			properties = PropertiesUtil
					.loadPropertiesFromClassPath("application.properties");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 获取配置文件中的中文
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @author: rentj
	 * @date:2015年4月7日 下午4:08:50
	 * @since 1.0.0
	 */
	public static Properties loadPropertiesCNFromClassPath(String path) {
		Properties properties = new Properties();
		InputStream inputStream = ConfigUtil.class.getResourceAsStream(path);

		try {
			// 用字符流来读取中文
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					inputStream, "utf-8"));
			properties.load(bf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 记录轮询时间，写入配置文件中
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param key
	 * @param time
	 *            轮询耗时
	 * @param date
	 *            轮询日期
	 * @author: rentj
	 * @date:2015年4月10日 下午4:12:36
	 * @since 1.0.0
	 */
	public static void writeProperties(String key, String time, String date) {
		try {
			Properties properties = new Properties();
			// 获取之前的内容
			String indexKey = ConfigUtil.propertiesInfo.getProperty(key);
			if (StringUtils.isNotEmpty(indexKey)) {
				// 如果之前内容不为空，就将轮询的日期以及时间添加到文件中
				StringBuffer value = new StringBuffer();
				properties.put(
						key,
						value.append(indexKey).append(";").append(date)
								.append(",").append(time));

				properties.store(new FileOutputStream(FILE_NAME), "轮询时间统计");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

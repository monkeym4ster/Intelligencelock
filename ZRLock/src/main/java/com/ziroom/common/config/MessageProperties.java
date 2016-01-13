package com.ziroom.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 获取消息信息
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 上午10:22:50
 * @since 1.0.0
 */
public class MessageProperties {

	// 读取Properties文件信息
	public static Properties properties = null;
	
	public final static String FILE_NAME = "properties/message.properties";
		
	static {

		try {
			properties = loadPropertiesCNFromClassPath(FILE_NAME);
		} catch (Exception e) {
		}
	}
	
	
	/**
	 * 获取配置文件中的中文
	 * 
	 * @param path 文件路径
	 * @return
	 * @author: rentj
	 * @date:2015年4月7日 下午4:08:50
	 * @since 1.0.0
	 */
	public static Properties loadPropertiesCNFromClassPath(String path) {
		Properties properties = new Properties();
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream(path);
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
}

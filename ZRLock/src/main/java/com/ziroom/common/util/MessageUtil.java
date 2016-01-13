package com.ziroom.common.util;

import com.ziroom.business.message.model.MessageInfo;
import com.ziroom.common.config.MessageProperties;
import com.ziroom.developer.json.JsonJacksonUtil;

/**
 * 获取消息的公用方法
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 上午10:52:47
 * @since 1.0.0
 */
public class MessageUtil {

	/**
	 * 获取消息
	 * @param request
	 * @param code
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月7日 上午11:01:08
	 * @since 1.0.0
	 */
	public static String getMessage(String ReqID, String code) {
		Object mes = MessageProperties.properties.get(code);
		if (mes != null) {
			MessageInfo message = new MessageInfo(ReqID, code, String.valueOf(mes));
			return JsonJacksonUtil.toJson(message);
		} else {
			MessageInfo message = new MessageInfo(ReqID, "-1", "提示信息未获取");
			return JsonJacksonUtil.toJson(message);
		}
	}
	
	
	/**
	 * 获取消息
	 * @param request
	 * @param code
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月7日 上午11:01:08
	 * @since 1.0.0
	 */
	public static String getMessage(String code) {
		Object mes = MessageProperties.properties.get(code);
		if (mes != null) {
			MessageInfo message = new MessageInfo(code, String.valueOf(mes));
			return JsonJacksonUtil.toJson(message);
		} else {
			MessageInfo message = new MessageInfo("-1", "提示信息未获取");
			return JsonJacksonUtil.toJson(message);
		}
	}
	
}

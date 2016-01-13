package com.ziroom.common.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


/**
 * 
 * 公用控制层
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年8月31日 下午3:01:56
 * @since 1.0.0
 */
public class BaseController {
	
	/**
	 * 
	 * 获取登录用户的权限 标识
	 * @param request
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年8月31日 下午3:37:59
	 * @since 1.0.0
	 */
	public String getRoleAdmin(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("adminFlag");
	}
	
	/**
	 * 解决IE下返回JSON成文件下载
	 * @param returnMap
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年11月24日 下午3:47:29
	 * @since 1.0.0
	 */
	public ResponseEntity<Map<String, Object>> returnResponse(Map<String, Object> returnMap) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		return new ResponseEntity<Map<String, Object>>(returnMap, headers, HttpStatus.OK);
	}
}

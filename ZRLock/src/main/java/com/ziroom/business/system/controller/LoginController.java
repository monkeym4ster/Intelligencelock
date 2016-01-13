package com.ziroom.business.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录界面控制层
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月5日 下午12:31:16
 * @since 1.0.0
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {

	/**
	 * 跳转到登录界面
	 * @param request
	 * @param response
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月5日 下午12:33:01
	 * @since 1.0.0
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, 
			HttpServletResponse response,
			ModelMap map) {
		return "system/login";
	}
	
	/**
	 * 登录智能锁系统
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月5日 下午10:04:33
	 * @since 1.0.0
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, 
			HttpServletResponse response,
			ModelMap map) {
		return "system/index";
	}
}

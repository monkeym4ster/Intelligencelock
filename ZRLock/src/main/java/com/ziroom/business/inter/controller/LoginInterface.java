package com.ziroom.business.inter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziroom.business.system.model.User;
import com.ziroom.business.system.service.UserService;
import com.ziroom.common.constant.SysConstant;
import com.ziroom.common.util.MessageUtil;
import com.ziroom.common.util.RedisClient;
import com.ziroom.developer.json.JsonJacksonUtil;
import com.ziroom.developer.string.RandomString;
import com.ziroom.developer.string.ValidatorUtil;


/**
 * 登录接口
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月6日 下午2:34:30
 * @since 1.0.0
 */
@Controller
public class LoginInterface {
	
	private static final String LOGIN_RANDOM_PREFIX = "login-";
	@Resource
	private UserService userService;

	/**
	 * 登录接口方法
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月6日 下午2:36:42
	 * @since 1.0.0
	 */
	@ResponseBody
	@RequestMapping(value="/account/v1/login", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String from = request.getParameter("from");
		// 获取请求的唯一标识
		String ReqID = RandomString.getPassword(10);
		if (StringUtils.isBlank(username)
				|| StringUtils.isBlank(password)
				|| StringUtils.isBlank(from)) {
			return MessageUtil.getMessage(ReqID, "14001");
		}
		
		if (ValidatorUtil.isSize(username, 0, 16)
				|| ValidatorUtil.isSize(username, 6, 13)) {
			return MessageUtil.getMessage(ReqID, "14001");
		}
		
		try {
			// 智能锁系统登录
			if (StringUtils.equals(SysConstant.ZLS, from)) {
				Map<String, String> authUserMap = new HashMap<String, String>();
				authUserMap.put("username", username);
				authUserMap.put("password", password);
				authUserMap.put("from", from);
				User user = userService.authUser(authUserMap);
				if (user == null) {
					return MessageUtil.getMessage(ReqID, "4019");
				}
				
				// 生成Token
				Map<String, String> genTokenMap = new HashMap<String, String>();
				genTokenMap.put("username", username);
				String token = userService.genToken(genTokenMap);
				
				// 设置Token
				user.setAccess_token(token);
				
				String redis_key = LOGIN_RANDOM_PREFIX + username;
				
				RedisClient redisClient = RedisClient.getInstance();
				redisClient.set(redis_key, JsonJacksonUtil.toJson(user), 30 * 24 * 60 * 60);
				
				
				
				
			} else {
				
			}
		} catch (Exception e) {
			return MessageUtil.getMessage(ReqID, "5000");
		}
		
		String mes = MessageUtil.getMessage(ReqID, "1000");
		return mes;
	}
}

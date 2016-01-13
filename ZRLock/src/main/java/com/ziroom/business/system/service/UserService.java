package com.ziroom.business.system.service;

import java.util.Map;

import com.ziroom.business.system.model.User;

public interface UserService {

	/**
	 * 通过条件获取用户信息
	 * @param authUserMap
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月7日 下午12:33:42
	 * @since 1.0.0
	 */
	public User authUser(Map<String, String> authUserMap);

	/**
	 * 生成Token
	 * @param genTokenMap
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月7日 下午2:39:08
	 * @since 1.0.0
	 */
	public String genToken(Map<String, String> genTokenMap);

}

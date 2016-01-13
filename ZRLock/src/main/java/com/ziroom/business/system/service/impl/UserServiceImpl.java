package com.ziroom.business.system.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ziroom.business.system.dao.UserDao;
import com.ziroom.business.system.model.User;
import com.ziroom.business.system.service.UserService;
import com.ziroom.common.constant.SysConstant;

/**
 * 用户服务层
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 下午12:26:35
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	/**
	 * 通过条件获取用户信息
	 * @param authUserMap
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月7日 下午12:33:42
	 * @since 1.0.0
	 */
	public User authUser(Map<String, String> authUserMap) {
		
		Criteria criteria = new Criteria();
		Object username = authUserMap.get("username");
		criteria.andOperator(Criteria.where("username").is(username));
		return userDao.findOne(Query.query(criteria), SysConstant.COLLECTION_HOUSE);
	}
	
	/**
	 * 生成Token
	 * @param genTokenMap
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月7日 下午2:39:08
	 * @since 1.0.0
	 */
	public String genToken(Map<String, String> genTokenMap) {
		String username = genTokenMap.get("username");
		
		// ??? Node.js var cipher = crypto.createCipher('aes-256-cbc', ACCESS_TOKEN_PREFIX); 加密
		
		
		return "";
	}
}

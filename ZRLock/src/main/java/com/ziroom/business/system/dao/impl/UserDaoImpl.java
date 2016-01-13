package com.ziroom.business.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ziroom.business.system.dao.UserDao;
import com.ziroom.business.system.model.User;
import com.ziroom.common.base.dao.impl.BaseMongoDaoImpl;

/**
 * 用户数据层实现
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 下午12:24:38
 * @since 1.0.0
 */
@Repository
public class UserDaoImpl extends BaseMongoDaoImpl<User> implements UserDao {

	
}

package com.ziroom.developer.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源读取工具类
 * @author: 张志宏
 * @date:2015年2月27日 上午9:37:29
 * @since 1.0.0
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	@Override
    public Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }

	
	
}

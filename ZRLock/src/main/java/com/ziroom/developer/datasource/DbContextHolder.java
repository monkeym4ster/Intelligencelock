package com.ziroom.developer.datasource;

/**
 * 多数据源实现
 * 
 * @author: 张志宏
 * @date:2015年3月1日 下午12:05:05
 * @since 1.0.0
 */
public abstract class DbContextHolder {

	//线程安全的ThreadLocal
    private static final ThreadLocal<String> CONTEXTHOLDER = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        CONTEXTHOLDER.set(dbType);
    }

    public static String getDbType() {
        return ((String)CONTEXTHOLDER.get());
    }
    public static void clearDbType() {
        CONTEXTHOLDER.remove();
    }
}

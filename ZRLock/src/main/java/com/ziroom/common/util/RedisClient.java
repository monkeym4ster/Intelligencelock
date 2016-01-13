package com.ziroom.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ziroom.common.config.ConfigUtil;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;



/**
 * redis客户端
 * 
 * @author: liuxm
 * @date:2015年2月2日 下午7:26:22
 * @since 1.0.0
 */
public class RedisClient {
	// 日志输出
	private final static transient Log logger = LogFactory.getLog(RedisClient.class);
	
	private static final String REDIS_POOL_MAXACTIVE = "redis.pool.maxActive";
	private static final String REDIS_POOL_MAXIDLE = "redis.pool.maxIdle";
	private static final String REDIS_POOL_MAXWAIT = "redis.pool.maxWait";
	private static final String REDIS_POOL_TESTONBORROW = "redis.pool.testOnBorrow";
	private static final String REDIS_IP = "redis.ip";
	private static final String REDIS_PORT = "redis.port";
	
	// redis客户端
	private static RedisClient client = null;

	// redis池
	private ShardedJedisPool pool;

	private boolean isInit = false;

	private RedisClient() {
		// 初始化redis池
		init();
	}

	// 单例返回 RedisClient
	public static RedisClient getInstance() {
		if (client == null) {
			synchronized (RedisClient.class) {
				if (client == null) {
					client = new RedisClient();
				}
			}
		}
		return client;
	}

	// 初始化 redis池
	private synchronized void init() {
		if (isInit == false) {
			// 读取配置文件
			JedisPoolConfig cfg = new JedisPoolConfig();
			String maxActive = ConfigUtil.properties.getProperty(REDIS_POOL_MAXACTIVE);
			if (StringUtils.isNotBlank(maxActive)) {
				try {
					int _maxActive = Integer.parseInt(maxActive);
					cfg.setMaxIdle(_maxActive);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			String maxIdle = ConfigUtil.properties.getProperty(REDIS_POOL_MAXIDLE);
			if (StringUtils.isNotBlank(maxIdle)) {
				try {
					int _maxIdle = Integer.parseInt(maxActive);
					cfg.setMaxIdle(_maxIdle);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			String maxWait = ConfigUtil.properties.getProperty(REDIS_POOL_MAXWAIT);
			if (StringUtils.isNotBlank(maxWait)) {
				try {
					int _maxWait = Integer.parseInt(maxWait);
					cfg.setMaxWaitMillis(_maxWait);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			String testOnBorrow = ConfigUtil.properties.getProperty(REDIS_POOL_TESTONBORROW);
			if (StringUtils.isNotBlank(testOnBorrow)) {
				try {
					boolean _testOnBorrow = Boolean.parseBoolean(testOnBorrow);
					cfg.setTestOnBorrow(_testOnBorrow);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				cfg.setTestOnBorrow(true);
			}

			String redisIp = ConfigUtil.properties.getProperty(REDIS_IP);

			// redis 端口地址
			String redisPort = ConfigUtil.properties.getProperty(REDIS_PORT);

			List<JedisShardInfo> shareInfoList = new ArrayList<JedisShardInfo>();

			JedisShardInfo shareInfo = new JedisShardInfo(redisIp, redisPort);
			shareInfoList.add(shareInfo);

			pool = new ShardedJedisPool(cfg, shareInfoList);
			isInit = true;
		}

	}

	/**
	 * 设置值
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 * @return
	 */
	public String set(String key, String value, int timeout) {
		ShardedJedis jedis = pool.getResource();
		// 现将value数据转成json格式
		String result = jedis.set(key, value);
		if (timeout > 0) {
			jedis.expire(key, timeout);
		}
		pool.returnResource(jedis);
		return result;
	}

	/**
	 * 根据key获取键值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		ShardedJedis jedis = pool.getResource();
		String value = jedis.get(key);
		// 将key再转化
		pool.returnResource(jedis);
		return value;
	}

	/**
	 * 删除键值
	 * 
	 * @param key
	 * @return
	 */
	public Long delete(String key) {
		ShardedJedis jedis = pool.getResource();
		Long del = jedis.del(key);
		pool.returnResource(jedis);
		return del;
	}

	/**
	 * 将 key 中储存的数字值增一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
	 * 
	 * @param key
	 * @return
	 */
	public Long increment(String key) {
		ShardedJedis jedis = pool.getResource();
		// 将 key 中储存的数字值增一
		Long incr = jedis.incr(key);
		pool.returnResource(jedis);
		return incr;
	}
	
	public String incrKey(String key) {
		RedisClient redisClient = RedisClient.getInstance();
		redisClient.increment(key);
		String KeyNum=redisClient.get(key);
		if(Long.parseLong(KeyNum)>99999998) {
			redisClient.set(key, "10000000", 0);
		}
		return KeyNum;
	}
	

	/**
	 * 
	 * 将消息放入队列
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String value){
		ShardedJedis jedis = pool.getResource();
		jedis.lpush(key, value);
		pool.returnResource(jedis);
	}
	/**
	 * 
	 * 从消息队列取出消息
	 * @param key
	 */
	public String rpop(String key){
		ShardedJedis jedis = pool.getResource();
		String msg = jedis.rpop(key);
		pool.returnResource(jedis);
		return msg;
	}
	


	public static void main(String[] args) {
		RedisClient redisClient = new RedisClient();
		redisClient.delete("ST-2258777-ETIMLzc5PKgWpkFW21v0-cas.ziroom.com");
//		redisClient.set("FINANCE_PULL_DATA_TIME", "2015-01-27 00:13:03", 0);
//		String room_before_day = redisClient.get("FINANCE_PULL_DATA_TIME");
//		System.out.println(room_before_day);
		//sysId+bizNo+timestampt
		// redisClient.set("sync.ypay.last.time", "2015-03-30 19:50:00", 0);
		/*System.out.println(redisClient.get("sync.ypay.last.time"));
		System.out.println(redisClient.get("sync.wtpay.last.time"));
		//String return_url = redisClient.get("09140914091409140947");
		redisClient.set("count_test_gjk", "2000000000000000000", 0);
		redisClient.increment("count_test_gjk");
		System.out.println(redisClient.get("count_test_gjk"));*/
	}
}

package com.ziroom.developer.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * Jackson JSON工具类
 * 		<pre>
 * 			1、jackson-core、jackson-databind、jackson-annotations
 * 			2、版本：2.X
 * 		</pre>
 * @author: 张志宏
 * @date:2015年7月2日 下午2:20:31
 * @since 1.0.0
 */
public class JsonJacksonUtil {
	
	/** 日志 */
	private static Logger logger = Logger.getLogger(JsonJacksonUtil.class);
	
    private static final ObjectMapper objectMapper;
    
    static {
        objectMapper = new ObjectMapper();
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
    }
    
    /**
     * <p>
     * 		功能：将JSON转换成类
     * </p>
     * @param json JSON串
     * @param clazz 需要转换的实体类
     * @return T 返回实体类
     * @author: 张志宏
     * @date:2015年7月2日 下午2:31:54
     * @since 1.0.0
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * <p>
     * 		功能：将实体类属性转换成String
     * </p>
     * <pre>
     * String json = JsonJacksonUtil.toJson(user);
     * </pre>
     * 
     * @param entity 需要转换的实体类
     * @return
     * @author: 张志宏
     * @date:2015年7月2日 下午2:33:05
     * @since 1.0.0
     */
    public static <T> String toJson(T entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * <p>
     * 		功能：将json转换成对应的集合
     * </p>
     * 
     * <pre>
     * 		Map<String, Object> genericData = JsonJacksonUtil.toCollection(json, new TypeReference<Map<String, Object>>(){});
     * 		List<Object> listData = JsonJacksonUtil.toCollection(json, new TypeReference<List<Object>>(){});
     * </pre>
     * 
     * @param json 
     * @param typeReference
     * @return
     * @author: 张志宏
     * @date:2015年7月2日 下午2:41:37
     * @since 1.0.0
     */
    public static <T> T toCollection(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
    
    /**
     * 
     * 将JSON转换成Map
     * @param jsonStr
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年10月7日 下午5:47:53
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
	public static <T> Map<String, Object> json2map(String jsonStr) {  
        try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}  
        return null;
    }  
    
    /**
     * 
     * 将JSON转换成Map<key, javabean>
     * @param jsonStr
     * @param clazz
     * @return
     * @throws Exception
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年10月7日 下午5:51:56
     * @since 1.0.0
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {  
		try {
			Map<String, Map<String, Object>> map = objectMapper.readValue(jsonStr,  
			        new TypeReference<Map<String, T>>() {  
			        });
		    Map<String, T> result = new HashMap<String, T>();  
	        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {  
	            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));  
	        }  
	        return result;  
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}  
       return null;
    }
    
    /**
     * 
     * 将JSON转换成List<javaBean>
     * @param jsonArrayStr
     * @param clazz
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年10月7日 下午5:50:50
     * @since 1.0.0
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {  
		try {
			List<Map<String, Object>> list = objectMapper.readValue(jsonArrayStr,  
			        new TypeReference<List<T>>() {  
			        });
			List<T> result = new ArrayList<T>();  
	        for (Map<String, Object> map : list) {  
	            result.add(map2pojo(map, clazz));  
	        }  
	        return result;  
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}  
        return null;
    }  
  
    /**
     * 
     * 将Map转换成javaBean
     * @param map
     * @param clazz
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年10月7日 下午5:49:32
     * @since 1.0.0
     */
    @SuppressWarnings("rawtypes")
	public static <T> T map2pojo(Map map, Class<T> clazz) {  
        return objectMapper.convertValue(map, clazz);  
    }  
    
    /**
     * 
     * 将List转换成JSON
     * @param list
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年8月8日 下午8:38:51
     * @since 1.0.0
     */
    public static <T> String listToJson(List<T> list) {
    	String json = null;
    	try {
			json = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
		}
		return json;
    	
    }
    
    public static void main(String[] args) {
    	// 将对象转换成JSON
    	/*User user = new User();
    	user.setName("测试");
    	user.setBrither(new Date());
    	user.setAge(29);
    	String json = JsonJacksonUtil.toJson(user);
    	System.out.println(json);*/
    	
    	// 将JSON转换成对象
    	/*String jsonStr = "{\"name\":\"测试\",\"brither\":\"2015-07-02 15:07:24\",\"age\":29}";
    	User jsonUser = JsonJacksonUtil.toObject(jsonStr, User.class);
    	System.out.println(jsonUser.getName());
    	System.out.println(DateUtil.DateToString(jsonUser.getBrither(), DateStyle.YYYY_MM_DD_HH_MM_SS) );*/
    	
    	// 将JSON转换成对应的集合
    	/*String jsonStr = "{\"name\":\"测试\",\"brither\":\"2015-07-02 15:07:24\",\"age\":29}";
    	Map<String, Object> genericDataMap = JsonJacksonUtil.toCollection(jsonStr, new TypeReference<Map<String, Object>>(){});
    	Set<Entry<String, Object>> genericDataSet = genericDataMap.entrySet();
    	for (Entry<String, Object> data : genericDataSet) {
    		String key = data.getKey();
    		Object user = data.getValue();
    		System.out.println(key + ":" + user.toString());
    	}*/
    	
    	// 将List转换成Json
    	User user1 = new User("测试1", new Date(), 1);
    	User user2 = new User("测试2", new Date(), 2);
    	User user3 = new User("测试3", new Date(), 3);
    	List<User> userList = new ArrayList<User>();
    	userList.add(user1);
    	userList.add(user2);
    	userList.add(user3);
    	String json = listToJson(userList);
    	System.out.println(json);
    }
}

/**
 * 测试用
 * @author: 张志宏
 * @date:2015年7月2日 下午3:37:54
 * @since 1.0.0
 */
class User{
	private String name;
	private Date brither;
	private Integer age;
	
	public User(String name, Date brither, Integer age) {
		this.name = name;
		this.brither = brither;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBrither() {
		return brither;
	}
	public void setBrither(Date brither) {
		this.brither = brither;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}

package com.ziroom.developer.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 
 * fastjson工具类
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:21:14
 * @since 1.0.0
 */
public final class JsonUtil {

    public static final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ObjectMapper MAPPER;

    static {
        JSON.DEFFAULT_DATE_FORMAT = DEFAULT_DATA_FORMAT;
        MAPPER = internalJacksonGenerateMapper(JsonInclude.Include.NON_NULL);
    }

    /**
     * 将json通过类型转换成对象
     * <p/>
     * <pre class="code">
     * {@link com.ziroom.tech.scaffold.boot.util.json.JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
     * </pre>
     *
     * @param json  json字符串
     * @param clazz 泛型类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将json通过类型转换成对象
     * <p/>
     * <pre class="code">
     * {@link com.ziroom.tech.scaffold.boot.util.json.JsonUtil JsonUtil}.fromJacksonJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
     * </pre>
     *
     * @param json  json字符串
     * @param clazz 泛型类型
     * @return 返回对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJacksonJson(String json, Class<T> clazz) throws IOException {
        return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
    }

    /**
     * 将json通过类型转换成对象
     * <p/>
     * <pre class="code">
     * {@link com.ziroom.tech.scaffold.boot.util.json.JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
     * </pre>
     *
     * @param json          json字符串
     * @param typeReference 引用类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        return JSON.parseObject(json, typeReference);
    }

    /**
     * 将json通过类型转换成对象
     * <p/>
     * <pre class="code">
     * {@link com.ziroom.tech.scaffold.boot.util.json.JsonUtil JsonUtil}.fromJacksonJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
     * </pre>
     *
     * @param json          json字符串
     * @param typeReference 引用类型
     * @return 返回对象
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T fromJacksonJson(String json, com.fasterxml.jackson.core.type.TypeReference<?> typeReference) throws IOException {
        if (typeReference.getType().equals(String.class)) {
            return (T) json;
        } else {
            return  MAPPER.readValue(json, typeReference);
        }
    }

    /**
     * 将对象转换成json
     * <p/>
     * <pre>
     *     {@link com.ziroom.tech.scaffold.boot.util.json.JsonUtil JsonUtil}.toJson(user);
     * </pre>
     *
     * @param src 对象
     * @return 返回json字符串
     */
    public static <T> String toJson(T src) {
        return internalToFastJson(src, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 将对象转换成json
     * <p/>
     * <pre>
     *     {@link com.ziroom.tech.scaffold.boot.util.json.JsonUtil JsonUtil}.toJacksonJson(user);
     * </pre>
     *
     * @param src 对象
     * @return 返回json字符串
     */
    public static <T> String toJacksonJson(T src) throws IOException {
        return internalToJacksonJson(src);
    }

    private static <T> String internalToFastJson(T src, SerializerFeature... features) {
        return JSON.toJSONString(src, features);
    }

    private static <T> String internalToJacksonJson(T src) throws IOException {
        return MAPPER.writeValueAsString(src);
    }

    private static ObjectMapper internalJacksonGenerateMapper(JsonInclude.Include inclusion) {

        ObjectMapper customMapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        customMapper.setSerializationInclusion(inclusion);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        // 所有日期格式都统一为以下样式
        customMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATA_FORMAT));

        return customMapper;
    }
}

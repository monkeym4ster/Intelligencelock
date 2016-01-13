package com.ziroom.developer.httpclient.http.security;

/**
 * 
 * Hex算法
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:05:58
 * @since 1.0.0
 */
public class HexUtil {

    private static final String HEX_CHARS = "0123456789abcdef";

    public HexUtil() {
    }

    /**
     * 
     * 将字节转换成字符串
     * @param bytes
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年7月3日 下午4:06:35
     * @since 1.0.0
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        for (byte aByte : bytes) {
            buffer.append(HexUtil.HEX_CHARS.charAt(aByte >>> 4 & 0x0F));
            buffer.append(HexUtil.HEX_CHARS.charAt(aByte & 0x0F));
        }
        return buffer.toString();
    }

    /**
     * 
     * 将字符串转换成字节
     * @param string
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年7月3日 下午4:07:01
     * @since 1.0.0
     */
    public static byte[] toByteArray(String string) {
        byte[] buffer = new byte[string.length() / 2];
        int j = 0;
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) ((Character.digit(string.charAt(j++), 16) << 4) | Character.digit(string.charAt(j++), 16));
        }
        return buffer;
    }

    /**
     * 
     * 拼接参数
     * @param returnStr
     * @param paramId
     * @param paramValue
     * @return
     * @author: 张志宏
     * @email:  it_javasun@yeah.net
     * @date:2015年7月3日 下午4:07:44
     * @since 1.0.0
     */
    public static String appendParam(String returnStr, String paramId, String paramValue) {
        if (!returnStr.equals("")) {
            if (!paramValue.equals("")) {
                returnStr = returnStr + "&" + paramId + "=" + paramValue;
            }
        } else {
            if (!paramValue.equals("")) {
                returnStr = paramId + "=" + paramValue;
            }
        }
        return returnStr;
    }
}

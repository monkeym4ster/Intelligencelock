package com.ziroom.developer.crypt;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author liuxuming
 * 
 *	BASE64Encoder 加密，解密
 */
@SuppressWarnings("restriction")
public class DesBase64Tool {

	private static Cipher cipher = null; // 私鈅加密对象Cipher

	private static Logger log = Logger.getLogger(DesBase64Tool.class);

	static {
		try {
			/* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param message 要加密的数据
	 * @param keyString 密钥
	 * @return
	 */
	public static String desEncrypt(String message,String keyString) {
		String result = ""; // DES加密字符串
		String newResult = "";// 去掉换行符后的加密字符串
		try {
			log.info("要加密的字符串是：" + message + " 长度为：" + message.length());
			SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");// 获得密钥
			/* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
			BASE64Encoder enc = new BASE64Encoder();
			result = enc.encode(resultBytes);// 进行BASE64编码
			newResult = encryptReplace(result); // 去掉加密串中的换行符
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newResult;
	}

	
	@SuppressWarnings("unused")
	public static String AECDecrypt(String message,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		
		  SecretKey secretKey = null;//key对象

		/*AES算法*/
		secretKey = new SecretKeySpec(key.getBytes(), "AES");//获得密钥
		/*获得一个私鈅加密类Cipher，DESede-》AES算法，ECB是加密模式，PKCS5Padding是填充方式*/
		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
		return key;
		
	}
	
	/**
	 * 解密
	 * 
	 * @param message 加密的数据
	 * @param keyString 密钥
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String message,String keyString) throws Exception {
		String result = "";
		try {
			BASE64Decoder dec = new BASE64Decoder();
			message = decryptReplace(message);// 解密转换
			byte[] messageBytes = dec.decodeBuffer(message); // 进行BASE64编码
			SecretKey secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");// 获得密钥
			cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
			byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
			result = new String(resultBytes, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 加密时 替换 + 和 / 为 - 和 _
	 * 
	 * @author liuxm
	 * @param str
	 * @return
	 */
	public static String encryptReplace(String str) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc == 10 || asc == 13) {
				continue;
			}
			char c = str.charAt(i);
			if (c == '+') {
				c = '-';
			} else if (c == '/') {
				c = '_';
			}
			sb.append(c);

		}

		return sb.toString();
	}

	/**
	 * 解密时 将 - 和 _ 替换为 + 和 /
	 * 
	 * @author liuxm
	 * @param str
	 * @return
	 */
	public static String decryptReplace(String str) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc == 10 || asc == 13) {
				continue;
			}
			char c = str.charAt(i);
			if (c == '-') {
				c = '+';
			} else if (c == '_') {
				c = '/';
			}
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 解密时 将 # 替换为 + 
	 * 
	 * @author liuxm
	 * @param str
	 * @return
	 */
	public static String decryptReplaceAES(String str) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			int asc = str.charAt(i);
			if (asc == 10 || asc == 13) {
				continue;
			}
			char c = str.charAt(i);
			if (c == '#') {
				c = '+';
			} 
			sb.append(c);
		}
		return sb.toString();
	}	
	
	
	public static void main(String[] args) throws Exception {
		//测试获取账户接口
		/*JSONObject jsonObj = new JSONObject();
		jsonObj.put("uid", "c1543e4d-41df-adf4-0a73-03ad9b50c338");
		String url = "http://172.16.31.184:8080/ZRLock/fcPayVouchers/addFcPayVourchers.html";
		Map<String, String> mapJson = new HashMap<String, String>();
		mapJson.put("time_stamp", "1442999713607");
		mapJson.put("request_type", "I");
		
		MessageInfo message = new MessageInfo();
		message.setMesName("张三");
		String jsonResp = JsonJacksonUtil.toJson(message);
		String encryption = DesBase64Tool.desEncrypt(jsonResp, "8w091ql5l2tt6qxj3z0emh21");
		mapJson.put("encryption", encryption);

		String reJson = HttpClientUtil.doPost(url, mapJson, "utf-8");
		System.out.println(reJson);*/
		
		/*String jiami = desEncrypt(jsonObj.toString(),"8w091ql5l2tt6qxj3z0emh21");
		System.out.println(jiami);
		String jiemi = desDecrypt(jiami,"8w091ql5l2tt6qxj3z0emh21");
		System.out.println(jiemi);
		
		System.out.println(String.valueOf(10));*/
		
	}
}

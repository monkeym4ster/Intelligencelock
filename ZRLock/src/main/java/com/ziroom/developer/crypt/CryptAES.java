package com.ziroom.developer.crypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * ClassName: CryptAES
 * 
 * @Description: AES加密，解密
 * @author liuxm
 * @date 2014年11月21日
 */
public class CryptAES {

	private static final String AESTYPE = "AES/ECB/PKCS5Padding";

	/**
	 * @Description: AES 加密
	 * @param  keyStr 密钥
	 * @param  plainText 加密数据
	 * @return String 加密完数据
	 * @throws
	 * @author liuxm
	 * @date 2014年11月21日
	 */
	public static String AES_Encrypt(String keyStr, String plainText) {
		byte[] encrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance(AESTYPE);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypt = cipher.doFinal(plainText.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(encrypt));
	}

	/**
	 * @Description: 解密
	 * @param  keyStr 密钥
	 * @param  encryptData 解密数据 
	 * @return String  
	 * @throws
	 * @author liuxm
	 * @date 2014年11月21日
	 */
	public static String AES_Decrypt(String keyStr, String encryptData) {
		byte[] decrypt = null;
		try {
			Key key = generateKey(keyStr);
			Cipher cipher = Cipher.getInstance(AESTYPE);
			cipher.init(Cipher.DECRYPT_MODE, key);
			decrypt = cipher
					.doFinal(Base64.decodeBase64(encryptData.getBytes()));
		} catch (Exception e) {
			;
		}
		String decryptStr = new String(decrypt);
		if (StringUtils.isBlank(decryptStr)) {
			decryptStr="";
		} else {
			decryptStr = decryptStr.trim();
		}
		return decryptStr;
	}

	/**
	 * @Description: 封装KEY值
	 * @param key
	 * @param @throws Exception   
	 * @return Key  
	 * @throws
	 * @author liuxm
	 * @date 2014年11月21日
	 */
	private static Key generateKey(String key) throws Exception {
		
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			return keySpec;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		String keyStr = "8w091ql5l2tt6qxj";
		System.out.println(keyStr.length());
		String plainText = "this is a string will be AES_Encrypt";
		System.out.println(plainText);
		// 测试获取账户接口
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("uid", "c1543e4d-41df-adf4-0a73-03ad9b50c338");
		String encText = AES_Encrypt(keyStr, jsonObj.toString());
		String a  =  URLDecoder.decode("%2Bqsab4SEEfdBA68%2Bdl8nXp5e2hx5pmiULuMP%2FbQ3JBYQPLzbKvF9QNunmG0jfoY0", "UTF-8");
		String decString = AES_Decrypt(keyStr, a);

		System.out.println(jsonObj.toString().toString());
		System.out.println(encText);

		System.out.println(decString);

	}
}
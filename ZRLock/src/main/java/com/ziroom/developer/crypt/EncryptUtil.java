package com.ziroom.developer.crypt;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 加密解密调用工具类
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年9月17日 下午3:52:59
 * @since 1.0.0
 */
public class EncryptUtil {

	/**
	 * 
	 * 数据加密
	 * @param jsonStr 需要加密的串
	 * @param encryptionKey
	 * @param base64Key
	 * @param aesKey
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年9月17日 下午3:55:19
	 * @since 1.0.0
	 */
	public  Map<String,Object>  encryptionWay(
			String jsonStr,
			String encryptionKey,
			String base64Key,
			String aesKey
			){
		Map<String,Object> map = new HashMap<String,Object>();
		String encryption = "";
		//加密
		try{
			if(encryptionKey.equals(base64Key)){
				encryptionKey = "key   DesBase64Tool加密方式出现异常";
				encryption = DesBase64Tool.desEncrypt(jsonStr,base64Key);
			}else{
				encryptionKey = "key_ziroom   CryptAES加密方式出现异常";
				encryption = CryptAES.AES_Encrypt(aesKey,jsonStr);
			}
			map.put("encryption", encryption);
			map.put("error","");
			return map;
		}catch(Exception e){
			map.put("encryption","");
			map.put("error",encryptionKey+" 具体异常-----"+e);
			return map;
		}
	}
}

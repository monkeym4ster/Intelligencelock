package com.ziroom.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ziroom.common.config.ConfigUtil;

public class HostIpUtil {
	//
	private final static Logger logger = LoggerFactory
			.getLogger(HostIpUtil.class);

	public static Boolean getIpFlg(HttpServletRequest request) throws Exception {
		// 获取需要发短信主机的Ip地址
		// String ip = InetAddress.getLocalHost().getHostAddress();
		// String ip = request.getRemoteAddr();
		String ip = getIpAddr(request);
		logger.info(" 取的的客户的IP 为 " + ip);
		String ipsStr = ConfigUtil.properties.getProperty("invoke_finance_ips");
		logger.info(" 配置文件  iP ： " + ipsStr);

		String[] ips = ipsStr.split(",");

		boolean isNext = false;
		for (String _ip : ips) {
			logger.info(" 从配置文件中取的的ip 值  : " + _ip);
			if (_ip.equals(ip)) {
				isNext = true;
				break;
			}
		}
		if (!isNext) {
			logger.info("您还没有权限访问该系统，如需访问请联系管理员！");
		}
		return isNext;
	}

	public static String getHostIP() {
		String IFCONFIG = null;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						IFCONFIG = inetAddress.getHostAddress().toString();
					}

				}
			}
		} catch (SocketException ex) {
		}
		return IFCONFIG;
	}

	/**
	 * 访问者真实的IP地址
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author: liuxm
	 * @date:2014年12月4日 下午4:34:00
	 * @since 1.0.0
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}

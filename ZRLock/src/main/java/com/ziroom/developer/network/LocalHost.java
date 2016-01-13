package com.ziroom.developer.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 本机的网络信息
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:35:53
 * @since 1.0.0
 */
public class LocalHost {

    private static NetworkInterface localNetworkInterface;
    private static String hostName;
    private static String localIPAddress = "";

    static {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress address;
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.getName().equals("lo")) {
                    localNetworkInterface = networkInterface;
                    Enumeration<InetAddress> enumeration = localNetworkInterface.getInetAddresses();
                    while (enumeration.hasMoreElements()) {
                        address = enumeration.nextElement();
                        if (address instanceof Inet4Address) {
                            localIPAddress = address.getHostAddress();
                            break;
                        }
                    }
                    if (!enumeration.hasMoreElements()) {
                        if (StringUtils.isBlank(localIPAddress)) {
                            try {
                                localIPAddress = InetAddress.getLocalHost().getHostAddress();
                            } catch (UnknownHostException e) {
                            }
                        }
                    }
                    break;
                }
            }

        } catch (SocketException e) {
        }

        try {
            // 多数情况是因为本机ping计算机名 无法正确获取本机, 可以采用修改hosts的方式添加进去
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            if (StringUtils.isBlank(hostName)) {
                hostName = "error_" + localIPAddress;
            }
        }
    }

    /**
     * 取本机的网卡接口
     *
     * @return LocalNetworkInterface
     */
    public static NetworkInterface getLocalNetworkInterface() {
        return localNetworkInterface;
    }

    /**
     * 取本机的机器名称
     *
     * @return MachineName
     */
    public static String getMachineName() {
        return hostName;
    }

    /**
     * 获取本机ip
     *
     * @return LocalIP
     */
    public static String getLocalIP() {
        return localIPAddress;
    }
    
}

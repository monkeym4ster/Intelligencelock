package com.ziroom.developer.protocol.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ziroom.developer.protocol.Request;

/**
 * 
 * 公共请求对象
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:37:21
 * @since 1.0.0
 */
public class HttpRequest implements Request {

    /**
     * 请求ip
     */
    @JsonProperty(value = "request_ip")
    protected String requestIp;

    /**
     * 请求系统
     */
    @JsonProperty(value = "request_system")
    protected String requestSystem;

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getRequestSystem() {
        return requestSystem;
    }

    public void setRequestSystem(String requestSystem) {
        this.requestSystem = requestSystem;
    }
}

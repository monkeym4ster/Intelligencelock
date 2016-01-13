package com.ziroom.developer.protocol.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ziroom.developer.network.LocalHost;
import com.ziroom.developer.protocol.Response;

/**
 * 
 * 公共返回对象
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年7月3日 下午4:37:35
 * @since 1.0.0
 */
public class HttpResponse implements Response {

    /**
     * 请求服务器计算机名
     */
    @JsonProperty(value = "server_machine_name")
    protected String serverMachineName = LocalHost.getMachineName();

    /**
     * 请求服务器ip
     */
    @JsonProperty(value = "server_ip")
    protected String serverIp = LocalHost.getLocalIP();

    /**
     * 请求服务器时间
     */
    @JsonProperty(value = "server_current_time")
    protected String serverCurrentTime = String.valueOf(System.currentTimeMillis());

    /**
     * 返回码
     */
    @JsonProperty(value = "response_code")
    protected String responseCode;

    /**
     * 错误信息
     */
    @JsonProperty(value = "error_info")
    protected String errorInfo;

    /**
     * 错误信息
     */
    @JsonProperty(value = "message_info")
    protected String messageInfo;

    public HttpResponse() {
    }

    public HttpResponse(String responseCode) {
        this.responseCode = responseCode;
    }

    public HttpResponse(String responseCode, String errorInfo) {
        this.responseCode = responseCode;
        this.errorInfo = errorInfo;
    }

    public HttpResponse(String responseCode, String messageInfo, String errorInfo) {
        this.messageInfo = messageInfo;
        this.responseCode = responseCode;
        this.errorInfo = errorInfo;
    }

    public void setResponse(String responseCode, String errorInfo) {
        this.responseCode = responseCode;
        this.errorInfo = errorInfo;
    }

    public String getServerMachineName() {
        return serverMachineName;
    }

    public void setServerMachineName(String serverMachineName) {
        this.serverMachineName = serverMachineName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerCurrentTime() {
        return serverCurrentTime;
    }

    public void setServerCurrentTime(String serverCurrentTime) {
        this.serverCurrentTime = serverCurrentTime;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String messageInfo) {
        this.messageInfo = messageInfo;
    }
}

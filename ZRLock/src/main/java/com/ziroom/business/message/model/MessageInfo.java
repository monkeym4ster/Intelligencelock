package com.ziroom.business.message.model;

/**
 * 消息封装体
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 上午10:42:23
 * @since 1.0.0
 */
public class MessageInfo {

	private String ReqID;
	private String ErrNo;
	private String ErrMsg;
	
	public MessageInfo(){}
	
	public MessageInfo(String reqID, String errNo, String errMsg) {
		this.ReqID = reqID;
		this.ErrNo = errNo;
		this.ErrMsg = errMsg;
	}
	
	public MessageInfo(String errNo, String errMsg) {
		this.ErrNo = errNo;
		this.ErrMsg = errMsg;
	}

	public String getReqID() {
		return ReqID;
	}

	public void setReqID(String reqID) {
		ReqID = reqID;
	}

	public String getErrNo() {
		return ErrNo;
	}

	public void setErrNo(String errNo) {
		ErrNo = errNo;
	}

	public String getErrMsg() {
		return ErrMsg;
	}

	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	
	
}

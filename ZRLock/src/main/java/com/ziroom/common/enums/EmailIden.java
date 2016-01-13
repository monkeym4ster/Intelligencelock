package com.ziroom.common.enums;

/**
 * 邮件标识
 * @author: renrf
 * @email:  it_javasun@yeah.net
 * @date:2015年11月26日 下午6:42:51
 * @since 1.0.0
 */
public enum EmailIden {

	Monitor("monitor"),System("system");
	private String index;
	private EmailIden(String index){
		this.index = index;
	}
	public String getIndex(){
		return this.index;
	}
	public String toString(){
		return this.index;
	}
}

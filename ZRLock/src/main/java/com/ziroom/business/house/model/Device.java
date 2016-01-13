package com.ziroom.business.house.model;

/**
 * 智能锁设备
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年12月31日 下午2:49:12
 * @since 1.0.0
 */
public class Device {

	private String uuid;
	private String type;
	private String gateway;
	private String room_id;
	private String manufactory_id;
	private Integer onoff;
	private String except_cnt;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getManufactory_id() {
		return manufactory_id;
	}
	public void setManufactory_id(String manufactory_id) {
		this.manufactory_id = manufactory_id;
	}
	public String getExcept_cnt() {
		return except_cnt;
	}
	public void setExcept_cnt(String except_cnt) {
		this.except_cnt = except_cnt;
	}
	public Integer getOnoff() {
		return onoff;
	}
	public void setOnoff(Integer onoff) {
		this.onoff = onoff;
	}
	
}

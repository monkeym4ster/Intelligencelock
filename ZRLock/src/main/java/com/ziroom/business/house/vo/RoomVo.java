package com.ziroom.business.house.vo;


/**
 * 房间VO
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月8日 下午3:55:26
 * @since 1.0.0
 */
public class RoomVo {

	private String full_location;	// 行政地址
	private String house_code;	// 房源编号
	private String room_code;	// 房间编号
	private String lock_type;	// 锁类型 1：网关 2：外门锁 3：卧室锁
	private String lock_name;
	private String manufactory_name;	// 供应商名称
	private String steward_name;	// 管家姓名
	private String steward_phone;	// 管家电话
	
	public String getFull_location() {
		return full_location;
	}
	public void setFull_location(String full_location) {
		this.full_location = full_location;
	}
	public String getHouse_code() {
		return house_code;
	}
	public void setHouse_code(String house_code) {
		this.house_code = house_code;
	}
	public String getRoom_code() {
		return room_code;
	}
	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}
	public String getManufactory_name() {
		return manufactory_name;
	}
	public void setManufactory_name(String manufactory_name) {
		this.manufactory_name = manufactory_name;
	}
	public String getLock_type() {
		return lock_type;
	}
	public void setLock_type(String lock_type) {
		this.lock_type = lock_type;
	}
	public String getLock_name() {
		return lock_name;
	}
	public void setLock_name(String lock_name) {
		this.lock_name = lock_name;
	}
	public String getSteward_name() {
		return steward_name;
	}
	public void setSteward_name(String steward_name) {
		this.steward_name = steward_name;
	}
	public String getSteward_phone() {
		return steward_phone;
	}
	public void setSteward_phone(String steward_phone) {
		this.steward_phone = steward_phone;
	}
}

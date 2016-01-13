package com.ziroom.business.house.model;


/**
 * 房间信息
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年12月31日 下午2:20:33
 * @since 1.0.0
 */
public class Room {

	private String room_id;
	private String room_code;
	private String room_type;
	private String order_id;
	private String manufactory_id;
	private Integer install_state;
	private String plan_submit_time;
	private String plan_confirm_time;
	private String order_create_time;
	private String order_submit_time;
	private String arrival_time;
	
	private String installer;
	private String steward;
	private String configer;
	
	
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_code() {
		return room_code;
	}
	public void setRoom_code(String room_code) {
		this.room_code = room_code;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getManufactory_id() {
		return manufactory_id;
	}
	public void setManufactory_id(String manufactory_id) {
		this.manufactory_id = manufactory_id;
	}
	public String getPlan_submit_time() {
		return plan_submit_time;
	}
	public void setPlan_submit_time(String plan_submit_time) {
		this.plan_submit_time = plan_submit_time;
	}
	public String getPlan_confirm_time() {
		return plan_confirm_time;
	}
	public void setPlan_confirm_time(String plan_confirm_time) {
		this.plan_confirm_time = plan_confirm_time;
	}
	public String getOrder_create_time() {
		return order_create_time;
	}
	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}
	public String getOrder_submit_time() {
		return order_submit_time;
	}
	public void setOrder_submit_time(String order_submit_time) {
		this.order_submit_time = order_submit_time;
	}
	public String getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}
	public String getInstaller() {
		return installer;
	}
	public void setInstaller(String installer) {
		this.installer = installer;
	}
	public String getSteward() {
		return steward;
	}
	public void setSteward(String steward) {
		this.steward = steward;
	}
	public String getConfiger() {
		return configer;
	}
	public void setConfiger(String configer) {
		this.configer = configer;
	}
	public Integer getInstall_state() {
		return install_state;
	}
	public void setInstall_state(Integer install_state) {
		this.install_state = install_state;
	}
}

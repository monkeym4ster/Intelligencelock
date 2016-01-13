package com.ziroom.business.system.model;

import java.util.Date;

/**
 * 自如智能锁角色
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 下午6:21:03
 * @since 1.0.0
 */
public class Role {

	private String _id;
	private String role_id;
	private String role_name;
	private Double house_confirm;
	private Double house_install;
	private Double house_mgt;
	private Double house_edit;
	private Double manufactory_mgt;
	private Double role_mgt;
	private Double lock_mgt;
	private Double gateway_mgt;
	private Double user_mgt;
	private Double sys_log;
	private Double removed;
	private Double status;
	private Date mtime;
	private Date ctime;
	private Double house_maintain;
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Double getHouse_confirm() {
		return house_confirm;
	}
	public void setHouse_confirm(Double house_confirm) {
		this.house_confirm = house_confirm;
	}
	public Double getHouse_install() {
		return house_install;
	}
	public void setHouse_install(Double house_install) {
		this.house_install = house_install;
	}
	public Double getHouse_mgt() {
		return house_mgt;
	}
	public void setHouse_mgt(Double house_mgt) {
		this.house_mgt = house_mgt;
	}
	public Double getHouse_edit() {
		return house_edit;
	}
	public void setHouse_edit(Double house_edit) {
		this.house_edit = house_edit;
	}
	public Double getManufactory_mgt() {
		return manufactory_mgt;
	}
	public void setManufactory_mgt(Double manufactory_mgt) {
		this.manufactory_mgt = manufactory_mgt;
	}
	public Double getRole_mgt() {
		return role_mgt;
	}
	public void setRole_mgt(Double role_mgt) {
		this.role_mgt = role_mgt;
	}
	public Double getLock_mgt() {
		return lock_mgt;
	}
	public void setLock_mgt(Double lock_mgt) {
		this.lock_mgt = lock_mgt;
	}
	public Double getGateway_mgt() {
		return gateway_mgt;
	}
	public void setGateway_mgt(Double gateway_mgt) {
		this.gateway_mgt = gateway_mgt;
	}
	public Double getUser_mgt() {
		return user_mgt;
	}
	public void setUser_mgt(Double user_mgt) {
		this.user_mgt = user_mgt;
	}
	public Double getSys_log() {
		return sys_log;
	}
	public void setSys_log(Double sys_log) {
		this.sys_log = sys_log;
	}
	public Double getRemoved() {
		return removed;
	}
	public void setRemoved(Double removed) {
		this.removed = removed;
	}
	public Double getStatus() {
		return status;
	}
	public void setStatus(Double status) {
		this.status = status;
	}
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Double getHouse_maintain() {
		return house_maintain;
	}
	public void setHouse_maintain(Double house_maintain) {
		this.house_maintain = house_maintain;
	}
	
}

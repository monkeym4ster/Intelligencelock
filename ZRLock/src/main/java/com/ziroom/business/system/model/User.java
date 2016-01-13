package com.ziroom.business.system.model;

import java.util.Date;

/**
 * 
 * 智能锁用户信息
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月7日 下午12:16:37
 * @since 1.0.0
 */
public class User {

	private String _id;
	private String username;
	private String password;
	private String role_id;
	private String nickname;
	private String user_phone;
	private String employee_no;
	private String manufactory_id;
	private Integer status;
	private Integer removed;
	private Date ctime;
	private Date mtime;
	
	private String access_token;
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public String getManufactory_id() {
		return manufactory_id;
	}
	public void setManufactory_id(String manufactory_id) {
		this.manufactory_id = manufactory_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getMtime() {
		return mtime;
	}
	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}

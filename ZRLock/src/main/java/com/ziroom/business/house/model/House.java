package com.ziroom.business.house.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能锁房屋信息
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年12月31日 上午11:53:43
 * @since 1.0.0
 */
public class House {

	private String _id;
	private String house_id;
	private String house_code;
	private String house_type;
	private String city;
	private String city_code;
	private String district;
	private String district_code;
	private String block;
	private String full_location;
	private String removed;
	private Date mtime;
	private Date ctime;
	
	private Map<String, Object> passwords = new LinkedHashMap<String, Object>();
	
	private List<Room> rooms = new ArrayList<Room>();
	private List<Device> devices = new ArrayList<Device>();
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getHouse_code() {
		return house_code;
	}
	public void setHouse_code(String house_code) {
		this.house_code = house_code;
	}
	public String getHouse_type() {
		return house_type;
	}
	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDistrict_code() {
		return district_code;
	}
	public void setDistrict_code(String district_code) {
		this.district_code = district_code;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getFull_location() {
		return full_location;
	}
	public void setFull_location(String full_location) {
		this.full_location = full_location;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
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
	public Map<String, Object> getPasswords() {
		return passwords;
	}
	public void setPasswords(Map<String, Object> passwords) {
		this.passwords = passwords;
	}
}

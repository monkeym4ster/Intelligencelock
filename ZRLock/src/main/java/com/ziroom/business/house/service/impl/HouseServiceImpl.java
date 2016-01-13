package com.ziroom.business.house.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ziroom.business.house.dao.HouseDao;
import com.ziroom.business.house.model.Device;
import com.ziroom.business.house.model.House;
import com.ziroom.business.house.model.Room;
import com.ziroom.business.house.model.Steward;
import com.ziroom.business.house.service.HouseService;
import com.ziroom.business.house.vo.RoomVo;
import com.ziroom.common.constant.SysConstant;
import com.ziroom.developer.json.JsonJacksonUtil;

@Service
public class HouseServiceImpl implements HouseService {
	
	@Resource
	private HouseDao houseDao;
	
	/**
	 * 根据条件查询离线锁列表
	 * @param whereMap
	 * @return
	 * @throws Exception
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年12月31日 上午11:56:02
	 * @since 1.0.0
	 */
	public Map<String, List<RoomVo>> findOffDeviceList(Map<String, Object> whereMap) throws Exception {
		
		Map<String, List<RoomVo>> mapRoomVo = new HashMap<String, List<RoomVo>>();
		try {
			// 查询网关离线的房源
			Query query = new Query();
			query.addCriteria(Criteria.where("rooms.room_type").is("out"));
			query.addCriteria(Criteria.where("rooms.install_state").is(5));
			List<House> houseList = houseDao.find(query, SysConstant.COLLECTION_HOUSE);
			
			// 网关离线
			List<RoomVo> gatewayList = new ArrayList<RoomVo>();
			// 外门锁、卧室锁离线
			List<RoomVo> lockList = new ArrayList<RoomVo>();
			// 没有离线、在线标识的
			List<RoomVo> unoffList = new ArrayList<RoomVo>();
			for (House house : houseList) {
				List<Room> roomList = house.getRooms();
				List<Device> devicesList = house.getDevices();
				
				String house_code = house.getHouse_code();
				if (StringUtils.isBlank(house_code)) {
					break;
				}
				String full_location = house.getFull_location();
				
				List<Room> roomInList = new ArrayList<Room>();
				for (Room room : roomList) {
					// 可管理房源状态
					Integer install_state = room.getInstall_state();
					String room_type = room.getRoom_type();
					
					if (install_state != null && install_state == 5) {
						if (StringUtils.equals("in", room_type)) {
							roomInList.add(room);
						}
					}
				}
				
				
				if (devicesList != null && !devicesList.isEmpty()) {
					for (Device device : devicesList) {
						if (device != null) {
							String deviceType = device.getType();
							Integer onoff = device.getOnoff();
							String manufactory_id = device.getManufactory_id();
							String manufactory_name = "";
							if (StringUtils.equals(manufactory_id, "2262")) {
								manufactory_name = "火河门锁";
							} else if (StringUtils.equals(manufactory_id, "2259")) {
								manufactory_name = "云丁门锁";
							}
							
							if (onoff == null) {
								// 没有设备在线标识的默认为在线 
								/*String room_id = device.getRoom_id();
								for (Room room : roomList) {
									String roomId = room.getRoom_id();
									String room_code = room.getRoom_code();
									String stewardJson = room.getSteward();	// 管家信息
									if (StringUtils.equals(room_id, roomId)) {
										RoomVo unlockRoom = new RoomVo();
										unlockRoom.setHouse_code(house_code);
										unlockRoom.setFull_location(full_location);
										unlockRoom.setManufactory_name(manufactory_name);
										if (StringUtils.equals("gateway", deviceType)) {
											unlockRoom.setRoom_code(room_code+"_网关");
										} else {
											unlockRoom.setRoom_code(room_code+"_锁");
										}
										
										if (StringUtils.isNotBlank(stewardJson)) {
											Steward steward = JsonJacksonUtil.toObject(stewardJson, Steward.class);
											if (steward != null) {
												unlockRoom.setSteward_name(steward.getName());
												unlockRoom.setSteward_phone(steward.getPhone());
											}
										}
										
										unlockRoom.setLock_type("4");
										unoffList.add(unlockRoom);
									}
									
								}*/
							}  else {
								// boolean flag = (onoff != null && onoff == 2);
								// 网关离线
								if (StringUtils.equals("gateway", deviceType)
										&& onoff == 2
										) {
									RoomVo gatewayRoom = new RoomVo();
									gatewayRoom.setHouse_code(house_code);
									gatewayRoom.setFull_location(full_location);
									gatewayRoom.setManufactory_name(manufactory_name);
									if (roomList != null && !roomList.isEmpty()) {
										String stewardJson = roomList.get(0).getSteward();
										if (StringUtils.isNotBlank(stewardJson)) {
											Steward steward = JsonJacksonUtil.toObject(stewardJson, Steward.class);
											if (steward != null) {
												gatewayRoom.setSteward_name(steward.getName());
												gatewayRoom.setSteward_phone(steward.getPhone());
											}
										}
									}
									gatewayRoom.setLock_type("1");
									gatewayList.add(gatewayRoom);
									break;
								} 
								// 外门锁离线
								else if (StringUtils.equals("home_lock", deviceType)
										&& onoff == 2) {
									RoomVo homelockRoom = new RoomVo();
									homelockRoom.setHouse_code(house_code);
									homelockRoom.setFull_location(full_location);
									homelockRoom.setManufactory_name(manufactory_name);
									if (roomList != null && !roomList.isEmpty()) {
										String stewardJson = roomList.get(0).getSteward();
										if (StringUtils.isNotBlank(stewardJson)) {
											Steward steward = JsonJacksonUtil.toObject(stewardJson, Steward.class);
											if (steward != null) {
												homelockRoom.setSteward_name(steward.getName());
												homelockRoom.setSteward_phone(steward.getPhone());
											}
										}
									}
									homelockRoom.setLock_type("2");
									lockList.add(homelockRoom);
								} 
								// 卧室锁离线
								else if (StringUtils.equals("room_lock", deviceType)
										&& onoff == 2) {
									String room_id = device.getRoom_id();
									for (Room room : roomInList) {
										String roomId = room.getRoom_id();
										String room_code = room.getRoom_code();
										String stewardJson = room.getSteward();	// 管家信息
										
										if (StringUtils.equals(room_id, roomId)) {
											RoomVo roomlockRoom = new RoomVo();
											roomlockRoom.setHouse_code(house_code);
											roomlockRoom.setFull_location(full_location);
											roomlockRoom.setManufactory_name(manufactory_name);
											roomlockRoom.setRoom_code(room_code);
											roomlockRoom.setLock_type("3");
											
											if (StringUtils.isNotBlank(stewardJson)) {
												Steward steward = JsonJacksonUtil.toObject(stewardJson, Steward.class);
												if (steward != null) {
													roomlockRoom.setSteward_name(steward.getName());
													roomlockRoom.setSteward_phone(steward.getPhone());
												}
											}
											lockList.add(roomlockRoom);
										}
									}
								}
							}
						}
					}
				}
				
			}
			mapRoomVo.put("gatewayList", gatewayList);
			mapRoomVo.put("lockList", lockList);
			mapRoomVo.put("unoffList", unoffList);
			
			return mapRoomVo;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * 查询房屋信息
	 * @param wheresHouseMap
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午9:30:22
	 * @since 1.0.0
	 */
	public List<House> findHouseList(Map<String, Object> wheresHouseMap) throws Exception{
		
		try {
			Criteria criteria = new Criteria();
			return houseDao.find(Query.query(criteria), SysConstant.COLLECTION_HOUSE);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	/**
	 * 更新房屋数据
	 * @param whereMap
	 * @throws Exception
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午8:15:35
	 * @since 1.0.0
	 */
	public void updateHouse(Map<String, Object> whereMap) throws Exception {
		try {
			
			Object house_code = whereMap.get("house_code");
			Object city_name = whereMap.get("city_name");
			Object district = whereMap.get("district");
			Object block = whereMap.get("block");
			Object full_location = whereMap.get("full_location");
			if (house_code != null) {
				Query query = new Query();
				query.addCriteria(Criteria.where("house_code").is(house_code));
				Update update = new Update();
				update.set("city", city_name);
				update.set("district", district);
				update.set("block", block);
				update.set("full_location", full_location);
				houseDao.update(query, update, SysConstant.COLLECTION_HOUSE);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}

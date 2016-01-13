package com.ziroom.business.house.service;

import java.util.List;
import java.util.Map;

import com.ziroom.business.house.model.House;
import com.ziroom.business.house.vo.RoomVo;

/**
 * 智能锁房屋接口
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年12月31日 上午11:54:47
 * @since 1.0.0
 */
public interface HouseService {


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
	public Map<String, List<RoomVo>> findOffDeviceList(Map<String, Object> whereMap) throws Exception;

	/**
	 * 更新房屋数据
	 * @param whereMap
	 * @throws Exception
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午8:15:35
	 * @since 1.0.0
	 */
	public void updateHouse(Map<String, Object> whereMap) throws Exception;

	/**
	 * 查询房屋信息
	 * @param wheresHouseMap
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午9:30:22
	 * @since 1.0.0
	 */
	public List<House> findHouseList(Map<String, Object> wheresHouseMap) throws Exception;
}

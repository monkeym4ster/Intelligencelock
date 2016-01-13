package com.ziroom.business.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.ziroom.business.house.model.House;
import com.ziroom.business.house.service.HouseService;
import com.ziroom.common.config.ConfigUtil;
import com.ziroom.common.constant.SysConstant;
import com.ziroom.common.enums.CityEnum;
import com.ziroom.common.solrj.SolrClientUtil;
import com.ziroom.developer.date.DateUtil;

/**
 * house补全任务
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2016年1月4日 下午8:12:59
 * @since 1.0.0
 */
public class HouseCompleteTask {

	// 日志
	private final static Log LOGGER = LogFactory.getLog(HouseCompleteTask.class);
		
	@Resource
	private HouseService houseService;
	
	private final static String ROOM_INDEX = ConfigUtil.properties.getProperty("room_info_index");
	
	/**
	 * 
	 * House数据补全
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午8:14:09
	 * @since 1.0.0
	 */
	public void houseComplete(){
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>House数据补全轮询【开始时间】："+DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMDHMSS_EN));
		SolrClientUtil solrClient = SolrClientUtil.getInstance(ROOM_INDEX);
		try {
			Map<String, Object> wheresHouseMap = new HashMap<String, Object>();
			List<House> houseList = houseService.findHouseList(wheresHouseMap);
			for (House house : houseList) {
				String house_code = house.getHouse_code();
				// 查询Solr
				String queryStr = "house_code:"+house_code;
				SolrDocumentList solrList = solrClient.queryfieldList(queryStr, 0, 10);
				if (solrList != null && !solrList.isEmpty()) {
					SolrDocument doc = solrList.get(0);
					Object district_name = doc.getFieldValue("district_name");
					Object resblock_name = doc.getFieldValue("resblock_name");
					Object rating_address = doc.getFieldValue("rating_address");
					Object city_code = doc.getFieldValue("city_code");
					String city_name = CityEnum.getCityCodeValue(city_code);
					Map<String, Object> whereMap = new HashMap<String, Object>();
					whereMap.put("house_code", house_code);
					whereMap.put("city_name", city_name);
					whereMap.put("district", district_name);
					whereMap.put("block", resblock_name);
					whereMap.put("full_location", rating_address);
					houseService.updateHouse(whereMap);
				}
			}
		} catch (Exception e) {
			LOGGER.error("House数据补全轮询异常"+e.getMessage(), e);
		}
		LOGGER.info(">>>>>>>>>>>>>>>>>>>>House数据补全轮询【结束时间】："+DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMDHMSS_EN));
	}
}

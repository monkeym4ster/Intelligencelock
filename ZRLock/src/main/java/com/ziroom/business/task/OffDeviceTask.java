package com.ziroom.business.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ziroom.business.house.service.HouseService;
import com.ziroom.business.house.vo.RoomVo;
import com.ziroom.common.config.ConfigUtil;
import com.ziroom.common.config.GetProperties;
import com.ziroom.common.constant.SysConstant;
import com.ziroom.common.email.MailEntity;
import com.ziroom.common.email.SendMail;
import com.ziroom.developer.date.DateUtil;
import com.ziroom.developer.office.PoiExcelUtils;

/**
 * 导出离线设备信息
 * @author: 张志宏
 * @email:  it_javasun@yeah.net
 * @date:2015年12月31日 下午1:49:51
 * @since 1.0.0
 */
public class OffDeviceTask {
	
	// 日志
	private final static Log LOGGER = LogFactory.getLog(OffDeviceTask.class);
	
	@Resource
	private HouseService houseService;
	
	// 导出文件
	private static final String export_file = GetProperties.getValue("export_file");
	// 邮件配置
	private static final String host = ConfigUtil.properties.getProperty("ziroom.email.host", "smtp.homelink.com.cn");
	private static final String from = ConfigUtil.properties.getProperty("ziroom.email.from", "ziroom-ams@homelink.com.cn");
	private static final String username = ConfigUtil.properties.getProperty("ziroom.email.username", "ziroom-ams");
	private static final String password = ConfigUtil.properties.getProperty("ziroom.email.password", "homelink");
	private static final String tos = ConfigUtil.properties.getProperty("ziroom.email.to", "zhangzh_v@homelink.com.cn");
	private static final String ccs = ConfigUtil.properties.getProperty("ziroom.email.cc", "");
	
	
	/**
	 * 离线设备任务
	 * 		devices.onoff=1:在线
	 * 		devices.onoff=2:离线
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年12月31日 下午1:51:03
	 * @since 1.0.0
	 */
	public void offDevice() {
		LOGGER.info("=============== 离线设备导出轮询【开始时间】："+DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMDHMSS_EN));
		String now = DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMD_EN);
		Map<String, List<RoomVo>> houseMap = new HashMap<String, List<RoomVo>>();
		try {
			Map<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("devices.onoff", SysConstant.FLAG_INT_2);
			houseMap = houseService.findOffDeviceList(whereMap);
		} catch (Exception e) {
			LOGGER.error("离线设备导出轮询异常："+e.getMessage(), e);
		}
		
		if (!houseMap.isEmpty()) {
			// 没有离线、在线标识的
			/*List<RoomVo> unoffListList = houseMap.get("unoffList");
			if (unoffListList != null && !unoffListList.isEmpty()) {
				// 导出网关离线设备
				String sheetName0 = "无离线、在线标识";
				String title0 = "智能锁无离线、在线标识房源";
				String filePath0 = export_file+"智能锁无离线、在线标识房源_"+now+".xlsx";
				String[] headers0 = {"离线类型@lock_name@5120","房源编号@house_code@5120","房间编号@room_code@2048","行政地址@full_location@17920", "供应商@manufactory_name@5120", "管家姓名@steward_name@5120", "管家电话@steward_phone@5120"};
				String unoffPath = exportExcel(unoffListList, sheetName0, title0, filePath0, headers0);
				
				    int num=4;
					while (num > 0) {
						if (StringUtils.isNotEmpty(unoffPath)) {
							try {
								String title = "智能锁无离线、在线标识房源";
								String content = "<h1>"+now+"智能锁无离线、在线标识房源信息，请详情见附件！</h1>";
								sendEmail(title, content, unoffPath);
								break;
							} catch (Exception e) {
								--num;
								LOGGER.error("智能锁无离线、在线标识设备导出轮询异常："+e.getMessage(), e);
							}
						}
					}
				
				
			}*/
			
			
			// 网关离线
			List<RoomVo> gatewayOffList = houseMap.get("gatewayList");
			if (gatewayOffList != null && !gatewayOffList.isEmpty()) {
				// 导出网关离线设备
				String sheetName1 = "网关离线";
				String title1 = "智能锁网关离线房源";
				String filePath1 = export_file+"智能锁网关离线房源_"+now+".xlsx";
				String[] headers1 = {"离线类型@lock_name@5120","房源编号@house_code@5120","行政地址@full_location@17920", "供应商@manufactory_name@5120", "管家姓名@steward_name@5120", "管家电话@steward_phone@5120"};
				String gatewayPath = exportExcel(gatewayOffList, sheetName1, title1, filePath1, headers1);
				
				int num=4;
				while (num > 0) {
					/**
					 * 发送邮件
					 */
					if (StringUtils.isNotEmpty(gatewayPath)) {
						try {
							String title = "智能锁网关离线房源";
							String content = "<h1>"+now+"网关离线的房源信息，请详情见附件！</h1>";
							sendEmail(title, content, gatewayPath);
							break;
						} catch (Exception e) {
							--num;
							LOGGER.error("智能锁无离线、在线标识设备导出轮询异常："+e.getMessage(), e);
						}
					}
				}
			}
			
			
			// 锁离线
			List<RoomVo> lockOffList = houseMap.get("lockList");
			if (lockOffList != null && !lockOffList.isEmpty()) {
				// 导出锁离线设备
				String sheetName2 = "锁离线";
				String title2 = "智能锁锁离线房源";
				String filePath2 = export_file+"智能锁锁离线房源_"+now+".xlsx";
				String[] headers2 = {"离线类型@lock_name@5120","房源编号@house_code@5120","房间编号@room_code@2048","行政地址@full_location@17920", "供应商@manufactory_name@5120", "管家姓名@steward_name@5120", "管家电话@steward_phone@5120"};
				String lockOffPath = exportExcel(lockOffList, sheetName2, title2, filePath2, headers2);
				
				int num=4;
				while (num > 0) {
					/**
					 * 发送邮件
					 */
					if (StringUtils.isNotEmpty(lockOffPath)) {
						try {
							String title = "智能锁锁离线房源";
							String content = "<h1>"+now+"锁离线的房源信息，请详情见附件！</h1>";
							sendEmail(title, content, lockOffPath);
							break;
						} catch (Exception e) {
							--num;
							LOGGER.error("智能锁无离线、在线标识设备导出轮询异常："+e.getMessage(), e);
						}
					}
				}
			}
			
		}
		
		LOGGER.info("离线设备导出轮询【结束时间】："+DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMDHMSS_EN));
	}
	
	/**
	 * 导出离线设备Excel
	 * @param list
	 * @param sheetName
	 * @param title
	 * @param filePath
	 * @param headers
	 * @return String 文件路径
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午3:43:55
	 * @since 1.0.0
	 */
	private String exportExcel(List<RoomVo> list, String sheetName, String title, String filePath, String[] headers) {
		List<RoomVo> dataList = new ArrayList<RoomVo>();
		for (RoomVo room : list) {
			
			RoomVo roomVo = new RoomVo();
			roomVo.setHouse_code(room.getHouse_code());
			roomVo.setFull_location(room.getFull_location());
			roomVo.setRoom_code(room.getRoom_code());
			roomVo.setManufactory_name(room.getManufactory_name());
			String lockType = room.getLock_type();
			if (StringUtils.equals(lockType, "1")) {
				roomVo.setLock_name("网关离线");
			} else if (StringUtils.equals(lockType, "2")) {
				roomVo.setLock_name("外门锁离线");
			} else if (StringUtils.equals(lockType, "3")) {
				roomVo.setLock_name("卧室锁离线");
			} else if (StringUtils.equals(lockType, "4")) {
				roomVo.setLock_name("无离线、在线标识");
			}
			roomVo.setSteward_name(room.getSteward_name());
			roomVo.setSteward_phone(room.getSteward_phone());
			dataList.add(roomVo);
		}
		
		try {
			PoiExcelUtils.createExcel2FilePath(sheetName, title, filePath, headers, dataList);
		} catch (Exception e) {
			return null;
		}
		return filePath;
	}
	
	/**
	 * 发送邮件
	 * @param filePath
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2016年1月4日 下午4:06:30
	 * @since 1.0.0
	 */
	private String sendEmail(String title, String content, String filePath) {
		
		try {
			MailEntity emailEntity = new MailEntity();
			emailEntity.setHost(host);	// 邮件服务器
			emailEntity.setEmailFROM(from);	// 发件人
			emailEntity.setUsername(username);	// 用户名
			emailEntity.setPassward(password);	// 密码
			if (StringUtils.isNotBlank(tos)) {
				String[] to = tos.split(";");
				emailEntity.setEmailTO(to);	// 收件人
			}
			if (StringUtils.isNotBlank(ccs)) {
				String[] cc = ccs.split(";");
				emailEntity.setEmailCC(cc);	// 抄送人
			}
			emailEntity.setTitle(title);	// 邮件标题
			emailEntity.setContent(content);	// 发送内容
			emailEntity.setAdjunct(new File(filePath));	// 附件
			
			SendMail.sendEmail(emailEntity);
		} catch (Exception e) {
			return null;
		}
		return filePath;
	}
}

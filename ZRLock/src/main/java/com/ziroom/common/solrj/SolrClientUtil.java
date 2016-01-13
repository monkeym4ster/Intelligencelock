package com.ziroom.common.solrj;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.ziroom.common.util.StrUtils;


/**
 * Solr客户端工具类
 * 
 * @author: 张志宏
 * @date:2015年3月4日 上午10:13:37
 * @since 1.0.0
 */
public class SolrClientUtil {

	// 日志
	private final static transient Log LOGGER = LogFactory.getLog(SolrClientUtil.class);

	// Solr客户端对象
	private volatile static SolrClientUtil solrClientUtil = null;
	// SolrServer对象
	// private  HttpSolrServer solrServer = null;
	public HttpSolrClient solrServer = null;

	/**
	 * 初始化SolrClientUtil：双重锁校验
	 * 
	 * @return
	 * @author: 张志宏
	 * @date:2015年3月4日 上午10:17:10
	 * @since 1.0.0
	 */
	public static SolrClientUtil getInstance(String indexUrl) {
			synchronized (SolrClientUtil.class) {
					solrClientUtil = new SolrClientUtil(indexUrl);
			}
		return solrClientUtil;
	}

	public SolrClientUtil(String indexUrl) {
		if (indexUrl != null) {
			solrServer = new HttpSolrClient(indexUrl);
			solrServer.setSoTimeout(30000); // 超时时间以毫秒为单位: 30s
			solrServer.setConnectionTimeout(10000); // 超时时间以毫秒为单位: 10s
			solrServer.setDefaultMaxConnectionsPerHost(10); // 设置可以打开的一个主机的最大连接数任何给定的时间。
			solrServer.setMaxTotalConnections(100); // 在任何给定的时间被打开的最大连接数。
			solrServer.setFollowRedirects(false); //
			solrServer.setAllowCompression(true);
		}
	}
	
	/*************************************************** 操作方法实现 *************************************************************/
	/**
	 * 添加索引数据
	 * @param bean 实体bean（需要注解）
	 */
	public void saveOrUpdate(Object bean) {
		try {
			solrServer.addBean(bean);
			solrServer.commit();
		} catch (Exception e) {
			LOGGER.error("添加更新索引数据，出错：", e);
		} finally {
			// 释放资源
			System.runFinalization();
			System.gc();
		}
	}

	/**
	 * 添加索引数据
	 * @param list 实体bean集合（需要注解）
	 */
	public void saveOrUpdate(List<?> list) {
		try {
			for (Object o : list) {
				solrServer.addBean(o);
			}
			solrServer.commit();
		} catch (Exception e) {
			LOGGER.error("添加索引数据，出错：", e);
		} finally {
			// 释放资源
			System.runFinalization();
			System.gc();
		}
	}
	
	/**
	 * 删除索引数据
	 * @param queryStr
	 * @author: 张志宏 
	 * @date:2015年3月4日 下午7:21:03
	 * @since 1.0.0
	 */
	public boolean deleteByQuery(String queryStr) {
		boolean falg = false;
		try {
			solrServer.deleteByQuery(queryStr);
			solrServer.commit();
			falg = true;
		} catch (Exception e) {
			LOGGER.error("删除索引数据，出错:", e);
		} finally {
			// 释放资源
			System.runFinalization();
			System.gc();
		}
		return falg;
	}
	
	/**
	 * 根据ID删除索引数据
	 * @param idList ID集合
	 * @author: 张志宏 
	 * @date:2015年3月4日 下午7:21:03
	 * @since 1.0.0
	 */
	public boolean deleteByListId(List<String> idList) {
		boolean falg = false;
		try {
			solrServer.deleteById(idList);
			solrServer.commit();
			falg = true;
		} catch (Exception e) {
			LOGGER.error("删除索引数据，出错:", e);
		} finally {
			// 释放资源
			System.runFinalization();
			System.gc();
		}
		return falg;
	}

	/**
	 * 根据查询条件返回结果集合
	 * 
	 * @param queryStr solr查询语句
	 * @param fieldList 展示的字段集合
	 * @param descList 倒序的字段集合
	 * @param bean 实体
	 * @return
	 */
	public List<?> queryList(String queryStr, List<String> fieldList,
			List<String> descList, Class<?> bean, int pageNum, int pageSize) {
		List<?> list = null;
		try {
			SolrQuery query = new SolrQuery();
			// 查询的条件
			query.setQuery(queryStr);
			// 返回的属性（列）,不设置则返回所有
			if (fieldList != null && !fieldList.isEmpty()) {
				for (String filed : fieldList) {
					query.add(filed);
				}
			}
			// 设置排序项，升序还是降序
			if (descList != null && !descList.isEmpty()) {
				for (String desc : descList) {
					query.addSort(new SortClause(desc, SolrQuery.ORDER.desc));

				}
			}

			// start row 相当于mysql中的limit
			query.setStart(pageNum);
			query.setRows(pageSize);
			QueryResponse qrsp = solrServer.query(query);
			list = qrsp.getBeans(bean);
		} catch (Exception e) {
			LOGGER.error("根据条件查询索引数据，出错：", e);
		}

		return list;

	}

	/**
	 * 根据条件查询索引数据
	 * @param queryStr solr查询语句
	 * @param fieldList 展示的字段集合
	 * @param descList 倒序的字段集合
	 * @param ascList 正序的字段集合
	 * @param bean 实体
	 * @param hlStr 高亮的字段
	 * @param idStr 实体的id
	 * @param hlField 高亮的结果存放的字段
	 * @param pageNum 页码
	 * @param pageSize 每页显示的记录数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryListHightLight(String queryStr,
			List<String> fieldList, List<String> descList,
			List<String> ascList, Class<?> bean, String hlStr, String idStr,
			String hlField, int pageNum, int pageSize) {

		// 返回查询结果map
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<?> list = null;

		try {
			SolrQuery query = new SolrQuery();

			// 设置高亮
			query.setHighlight(true);
			query.setHighlightSimplePre("<span class='highlight'>");// 设置开头
			query.setHighlightSimplePost("</span>"); // 设置结尾
			// 设置高亮的哪些区域
			query.setParam("hl.fl", hlStr);
			// 查询的条件
			query.setQuery(queryStr);
			// 返回的属性（列）,不设置则返回所有
			if (!StrUtils.isNotNullOrBlank(fieldList)) {
				for (String filed : fieldList) {
					query.add(filed);
				}
			}

			// 设置排序项，降序
			if (!StrUtils.isNotNullOrBlank(descList)) {
				for (String desc : descList) {
					query.addSort(new SortClause(desc, SolrQuery.ORDER.desc));

				}
			}

			// 设置排序项，升序
			if (!StrUtils.isNotNullOrBlank(ascList)) {
				for (String asc : ascList) {
					query.addSort(new SortClause(asc, SolrQuery.ORDER.asc));

				}
			}

			// start row 相当于mysql中的limit
			query.setStart((pageNum - 1) * pageSize);
			query.setRows(pageSize);
			QueryResponse qrsp = solrServer.query(query);
			SolrDocumentList documentList = qrsp.getResults();

			// 符合条件的记录数
			int count = (int) documentList.getNumFound();
			// 页数
			int pageCount = count / pageSize + (count % pageSize > 0 ? 1 : 0);
			resultMap.put("count", count);
			resultMap.put("pageCount", pageCount);
			list = qrsp.getBeans(bean);
			// 处理高亮字段以及对应接收的属性值
			String[] hlStrs = hlStr.split(",");
			String[] hlFields = hlField.split(",");
			Object temp = null;
			String[] setMethod = new String[hlFields.length];

			for (int i = 0; i < hlStrs.length; i++) {
				setMethod[i] = "set"
						+ hlFields[i].toString().substring(0, 1).toUpperCase()
						+ hlFields[i].toString().substring(1);
			}

			for (int i = 0; i < list.size(); i++) {
				temp = list.get(i);
				// 通过反射获取id的值
				Field fildId = temp.getClass().getDeclaredField(idStr);
				fildId.setAccessible(true);
				Object id = (Object) fildId.get(temp);
				// 将存在高亮值的字段的值设置到对应的属性值中
				if (null != qrsp.getHighlighting().get(id)) {
					for (int j = 0; j < hlStrs.length; j++) {
						Object glConent = qrsp.getHighlighting().get(id)
								.get(hlStrs[j]);

						if (null != glConent) {
							bean.getMethod(setMethod[j], String.class).invoke(
									temp,
									glConent.toString().substring(1,
											glConent.toString().length() - 1));
						}
					}

				}

			}
		} catch (Exception e) {
			LOGGER.error("根据条件查询索引数据(高亮)，出错：", e);
		}
		resultMap.put("list", list);
		return resultMap;
	}

	
	/**
	 * 根据查询条件返回结果数量
	 * 
	 * @param queryStr solr查询语句
	 * @return
	 */
	public Long queryNum(String queryStr) {
		Long docNum=0l;
		try {
			SolrQuery query = new SolrQuery();
			// 查询的条件
			query.setQuery(queryStr);
			QueryResponse qrsp = solrServer.query(query);
			SolrDocumentList docs = qrsp.getResults();
			docNum=docs.getNumFound();
		} catch (Exception e) {
			LOGGER.error("根据条件查询索引数据，出错："+e.getMessage(), e);
		}

		return docNum;

	}
	
	/**
	 * 获取条件索引的数量
	 * <p><b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * @return
	 * @author: rentj 
	 * @date:2015年4月23日 下午2:28:06
	 * @since 1.0.0
	 */
	public Long queryFullNum(String queryStr) {
		Long docNum=0l;
		try {
			SolrQuery query = new SolrQuery();
			// 查询的条件
			query.setQuery(queryStr);
			QueryResponse qrsp = solrServer.query(query);
			SolrDocumentList docs = qrsp.getResults();
			docNum=docs.getNumFound();
		} catch (Exception e) {
			LOGGER.error("获取索引的所有数量，出错："+e.getMessage(), e);
		}
		return docNum;
	}
	
	/**
	 * 
	 * 根据查询条件返回solr集合
	 * <p><b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author: rentj 
	 * @date:2015年4月23日 下午4:04:19
	 * @since 1.0.0
	 */
	public SolrDocumentList queryfieldList(String queryStr,Integer pageNum, Integer pageSize) {
		SolrDocumentList docs=null;
		try {
			
			SolrQuery query = new SolrQuery();
			// 查询的条件
			query.setQuery(queryStr);
			// start row 相当于mysql中的limit
			query.setStart(pageNum);
			query.setRows(pageSize);
			QueryResponse qrsp = solrServer.query(query);
			
			//获取查询出的所有doc
			docs=qrsp.getResults();
			
		} catch (Exception e) {
			LOGGER.error("根据查询条件返回一个字段集合，出错：", e);
		}

		return docs;

	}
	
	/**
	 * 
	 * TODO
	 * @param docsList
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年8月24日 下午12:01:07
	 * @since 1.0.0
	 */
	public void saveOrUpdateDocument(Collection<SolrInputDocument> docsList) {
		try {
			for (SolrInputDocument doc: docsList) {
				solrServer.add(doc);
			}
			solrServer.commit();
		} catch (Exception e) {
			LOGGER.error("添加索引数据，出错：", e);
		} finally {
			// 释放资源
			System.runFinalization();
			System.gc();
		}
		
	}
	
	/**
	 * 
	 * 保存SolrInputDocument
	 * @param doc
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年8月24日 下午12:00:54
	 * @since 1.0.0
	 */
	public void saveOrUpdateDocument(SolrInputDocument doc) {
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (Exception e) {
			LOGGER.error("添加索引数据，出错：", e);
		} finally {
			// 释放资源
			System.runFinalization();
			System.gc();
		}
	} 
	
	
	/**
	 * 获取地铁附近的2个楼盘
	 * TODO 自如小区信息索引老逻辑
	 * <p><b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * @param subwayCode
	 * @return
	 * @author: rentj 
	 * @date:2015年4月27日 下午3:03:13
	 * @since 1.0.0
	 */
	public Map<String,String> getNearResblockBySubWay(String subwayCode){
		SolrQuery query = new SolrQuery();
		Map<String,String> resblockMap = new HashMap<String, String>();
		//group=true&group.field=resblock_id 分组查询 返回不同组的结果
		query.set("q", "subway_station_code:"+subwayCode);
		query.set("fl", "resblock_id,resblock_name");
		query.set("group", "true");
		query.set("group.field", "resblock_id");
		query.set("rows", "2");
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("solr's query：" + query.toString() );
		}
		
		try {
			QueryResponse response2 = solrServer.query(query);
			//获取分组后的结果
			GroupResponse groupResponse = response2.getGroupResponse(); 
			 if (groupResponse != null) {  
				 	//获取分组后的数据
	                List<GroupCommand> groupCommandList = groupResponse.getValues();  
	                for (GroupCommand groupCommand : groupCommandList) {   
	                    List<Group> groups = groupCommand.getValues();  
	                    for (int j=0 ,i=groups.size(); j<i ; j++ ) { 
	                    	//获取每个分组doc的集合
	                        SolrDocumentList list = groups.get(j).getResult();  
	                        if(list!=null && !list.isEmpty()){
	                        	//循环放入MAP中
	                        	for(int t=0,k=list.size(); t<k ;t++){
	                        		String resblockName = (String)list.get(t).getFieldValue("resblock_name");
	                        		String resblockId = (String) list.get(t).getFieldValue("resblock_id");
	                        		resblockMap.put("resblockId"+j, resblockId);
	                        		resblockMap.put("resblockName"+j, resblockName);
	                        	}
	                        	
	                        }
	                    }  
	                }  
	            }  
		} catch (SolrServerException | IOException e) {
			LOGGER.error("获取地铁附近的楼盘出错 ：", e);
		}
		return resblockMap;
	}
	
	/**
	 * 获取小区到最近地铁站的步行时间
	 * TODO 自如小区信息索引老逻辑
	 * <p><b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * @param queryStr
	 * @return
	 * @author: rentj 
	 * @date:2015年4月27日 下午3:27:59
	 * @since 1.0.0
	 */
	public int getWorkTime(String queryStr) {
		SolrQuery query = new SolrQuery();
		query.set("q", queryStr);
		query.set("fl", "walktime");
		query.set("rows", "1");
		int walktime=0;
		try {
			QueryResponse response = solrServer.query(query);
			SolrDocumentList list = response.getResults();
			if(list!=null && list.size()!=0){
				SolrDocument doc = list.get(0);
				walktime =  (Integer) doc.getFieldValue("walktime");
			}
		} catch (SolrServerException | IOException e) {
			LOGGER.error("获取小区到最近地铁站的步行时间 出错", e);
		}
		return walktime;
	}
}

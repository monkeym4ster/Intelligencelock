package com.ziroom.developer.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ziroom.common.constant.SysConstant;
import com.ziroom.developer.date.DateUtil;

/**
 * 
 * 上传文件工具类
 * 
 * @author: 张志宏
 * @email: it_javasun@yeah.net
 * @date:2015年9月2日 下午2:59:29
 * @since 1.0.0
 */
public class UploadFile {
	
	/** 日志 */
	private static Logger logger = Logger
			.getLogger(UploadFile.class);

	/**
	 * 
	 * 采用字节流用时较长
	 * 
	 * @param files
	 * @param request
	 * @return List<String> 文件保存路径列表
	 * @author: 张志宏
	 * @email: it_javasun@yeah.net
	 * @date:2015年9月2日 下午2:56:20
	 * @since 1.0.0
	 */
	public List<String> uploadFileStream(
				CommonsMultipartFile[] files,
				HttpServletRequest request,
				String basePath
			) {

		// 文件列表
		List<String> fileList = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			String filename = files[i].getOriginalFilename();
			if (logger.isDebugEnabled()) {
				logger.debug("上传前的文件名：" + filename);
			}

			if (!files[i].isEmpty()) {
				try {
					// 日期目录
					String dirPath = DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMD_EN);
					String orgFileName = new Date().getTime()+filename;
					String urlFile = basePath + "/" + dirPath;
					File file =new File(urlFile);  
					// 目录不存在&&是一个目录
					if (!file.exists()
							&& file .isDirectory()) {
						file.mkdirs();
					} else {
						return null;
					}
					
					// 拿到输出流，同时重命名上传的文件
					FileOutputStream os = new FileOutputStream(urlFile + "/" + orgFileName);
					// 拿到上传文件的输入流
					FileInputStream in = (FileInputStream) files[i].getInputStream();

					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}
					os.flush();
					os.close();
					in.close();
					
					// 获取上传文件的目录
					fileList.add(dirPath+orgFileName);

				} catch (Exception e) {
					logger.error(filename + "文件上传失败："+e.getMessage(), e);
				}
			}
		}
		return fileList;
	}

	/**
	 * 
	 * 采用springMVC解析器进行文件上传【最佳方案】
	 * @param request
	 * @param response
	 * @param basePath 文件上传路径
	 * @return
	 * @author: 张志宏
	 * @email:  it_javasun@yeah.net
	 * @date:2015年9月2日 下午4:09:07
	 * @since 1.0.0
	 */
	public static List<String> uploadFile(HttpServletRequest request,
			HttpServletResponse response,
			String basePath) {
		
		    // 文件列表
			List<String> fileList = new ArrayList<String>();
			
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				
				// 日期目录
				String dirPath = DateUtil.DateToString(new Date(), SysConstant.FORMAT_YMD_EN);
				String urlFile = basePath + "/" + dirPath;
				
				// 文件保存路径  
				File fileDir =new File(urlFile);  
				// 目录不存在&&是一个目录
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				
				while (iter.hasNext()) {
					try {
						// 取得上传文件
						MultipartFile file = multiRequest.getFile(iter.next());
						if (file != null) {
							// 取得当前上传文件的文件名称
							String myFileName = file.getOriginalFilename();
							// 重命名上传后的文件名
							String fileName = new Date().getTime() + myFileName;
							// 定义上传路径
							String path = urlFile + "/" + fileName;
							
							// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
							if (StringUtils.isNotBlank(myFileName.trim())) {
								File localFile = new File(path);
								file.transferTo(localFile);
							}
							
							// 赋值文件里列表
							fileList.add("/"+dirPath+"/"+fileName);
						}
					} catch (Exception e) {
						logger.error("文件上传失败："+e.getMessage(), e);
					}
				}

			}
		
		return fileList;
	}

}

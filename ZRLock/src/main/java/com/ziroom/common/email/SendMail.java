package com.ziroom.common.email;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ziroom.common.util.DateUtil;

/**
 * 邮件发送类
 * 
 * @author: liuxm
 * @date:2015年2月2日 下午7:27:36
 * @since 1.0.0
 */
public class SendMail {

	// 日志
	private final static transient Log logger = LogFactory
			.getLog(SendMail.class);
	
	/**
	 * 发送邮件方法
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param emailEntity
	 * @return
	 * @author: liuxm
	 * @date:2015年2月2日 下午7:34:01
	 * @since 1.0.0
	 */
	public static boolean sendEmail(String emailIden, String content) {
		logger.info("邮件发送开始！");
		boolean flag = false;
		try {
			HashMap<String, Object> whereMap = new HashMap<String, Object>();
			whereMap.put("emailIden", emailIden);
			

			/*String host = emailConfig.getEmailHost();
			String username = emailConfig.getEmailUsername();
			String password = DesBase64Tool.desDecrypt(emailConfig.getEmailPassword(), SysConstant.FINANCE_KEY);
			String emailFROM = emailConfig.getEmailSe();
			String emailTO = emailConfig.getEmailRe();
			String emailCC = emailConfig.getEmailCc();
			String title = emailConfig.getEmailTitle();*/
			
			String host = "";
			String username = "";
			String password = "";
			String emailFROM = "";
			String emailTO = "";
			String emailCC = "";
			String title = "";
			
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

			// 设定mail server "smtp.homelink.com.cn"
			senderImpl.setHost(host);
			// 建立邮件消息,发送简单邮件和html邮件的区别
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
			// multipart模式 为true时发送附件 可以设置html格式
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");

			// 设置收件人，寄件人
			messageHelper.setTo(emailTO.split(";"));
			messageHelper.setFrom(emailFROM);
			// 抄送人
			if (emailCC != null) {
				messageHelper.setCc(emailCC.split(";"));;
			}

			messageHelper.setSubject(title);
			// true 表示启动HTML格式的邮件
			messageHelper.setText(content, true);
			

			senderImpl.setUsername(username); // 根据自己的情况,设置username
			senderImpl.setPassword(password); // 根据自己的情况,
																// 设置password
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
			prop.put("mail.smtp.timeout", "25000");
			senderImpl.setJavaMailProperties(prop);
			// 发送邮件
			senderImpl.send(mailMessage);
			
			flag = true;
			logger.info("邮件发送完成！");
		} catch (Exception e) {
			flag = false;
			logger.error("发送邮件失败：" + e.getMessage(), e);
		}
		return flag;
	}
	/**
	 * 发送邮件方法
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param emailEntity
	 * @return
	 * @author: liuxm
	 * @date:2015年2月2日 下午7:34:01
	 * @since 1.0.0
	 */
	public static boolean sendEmail(MailEntity emailEntity) {
		logger.info("邮件发送开始！");
		boolean flag = false;
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

			// 设定mail server "smtp.homelink.com.cn"
			senderImpl.setHost(emailEntity.getHost());
			// 建立邮件消息,发送简单邮件和html邮件的区别
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
			// multipart模式 为true时发送附件 可以设置html格式
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");

			// 设置收件人，寄件人
			messageHelper.setTo(emailEntity.getEmailTO());
			messageHelper.setFrom(emailEntity.getEmailFROM());

			// 抄送人
			String[] emailCC = emailEntity.getEmailCC();
			if (emailCC != null && emailCC.length > 0) {
				messageHelper.setCc(emailCC);
			}

			messageHelper.setSubject(emailEntity.getTitle());
			// true 表示启动HTML格式的邮件
			messageHelper.setText(emailEntity.getContent(), true);

			// 附件
			File file = emailEntity.getAdjunct();
			if (file != null) {
				FileSystemResource fileResource = new FileSystemResource(file);
				// 这里的方法调用和插入图片是不同的。
				messageHelper.addAttachment(fileResource.getFilename(), file);
			}

			senderImpl.setUsername(emailEntity.getUsername()); // 根据自己的情况,设置username
			senderImpl.setPassword(emailEntity.getPassward()); // 根据自己的情况,
																// 设置password
			Properties prop = new Properties();
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
			prop.put("mail.smtp.timeout", "25000");
			senderImpl.setJavaMailProperties(prop);
			// 发送邮件
			senderImpl.send(mailMessage);
			flag = true;
			logger.info("邮件发送完成！");
		} catch (Exception e) {
			flag = false;
			logger.error("发送邮件失败：" + e.getMessage(), e);
		}
		return flag;
	}
	public static void main(String args[]) {
		MailEntity mailEntity = new MailEntity();
		// 邮件服务器
		mailEntity.setHost("smtp.homelink.com.cn");
		// 发件人
		mailEntity.setEmailFROM("gaojk4@ziroom.com");
		// 收件人
		String mail = "gaojk4@ziroom.com,805644939@qq.com";
		mailEntity.setEmailTO(mail.split(","));
		// 邮箱用户名
		mailEntity.setUsername("gaojk4");
		// 邮箱密码
		mailEntity.setPassward("20102575Gjk");
		// 邮件标题
		mailEntity.setTitle(DateUtil.getYesterday("", -1, DateUtil.DATE_FORMAT)
				+ "推送数据出了问题，看一看，看一看 =.=");
		// 邮件发送的内容
		mailEntity.setContent("<html><body><b>" + "testtest2"
				+ "</b></body></html>");

		try {
			SendMail.sendEmail(mailEntity);
		} catch (Exception e) {
			logger.error("发送邮件失败" + e.getMessage(), e);
		}
	}

}

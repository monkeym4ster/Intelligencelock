package com.ziroom.common.email;

import java.io.File;

/**
 * 
 * 邮件实体
 * 
 * @author: fanqx
 * @date:2015年11月11日 下午1:31:52
 * @since 1.0.0
 */
public class MailEntity {
	private File adjunct;	// 附件
	private String content;	// 发送内容
	private String[] emailCC;	// 抄送人
	private String emailFROM;	// 发件人
	private String[] emailTO;	// 收件人
	private String host;	// 邮件服务器
	private String title;	// 邮件标题
	private String username;	// 用户名
	private String passward;	// 密码

	public final File getAdjunct() {
		return adjunct;
	}

	public final void setAdjunct(File adjunct) {
		this.adjunct = adjunct;
	}

	public final String getContent() {
		return content;
	}

	public final void setContent(String content) {
		this.content = content;
	}

	public final String[] getEmailCC() {
		return emailCC;
	}

	public final void setEmailCC(String[] emailCC) {
		this.emailCC = emailCC;
	}

	public final String getEmailFROM() {
		return emailFROM;
	}

	public final void setEmailFROM(String emailFROM) {
		this.emailFROM = emailFROM;
	}

	public final String[] getEmailTO() {
		return emailTO;
	}

	public final void setEmailTO(String[] emailTO) {
		this.emailTO = emailTO;
	}

	public final String getHost() {
		return host;
	}

	public final void setHost(String host) {
		this.host = host;
	}

	public final String getPassward() {
		return passward;
	}

	public final void setPassward(String passward) {
		this.passward = passward;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

}

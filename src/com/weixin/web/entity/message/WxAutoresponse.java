package com.weixin.web.entity.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Generated 2014-8-7 14:54:17 by Hibernate Tools 3.2.2.GA

/**
 * WxAutoresponse generated by hbm2java
 */
@Entity
@Table(name = "wx_autoresponse")
public class WxAutoresponse implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "accountId")
	private String accountId;
	
	@Column(name = "addTime")
	private String addTime;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "msgType")
	private String msgType;
	
	@Column(name = "resTemplateId")
	private String resTemplateId;
	
	@Column(name = "templateName")
	private String templateName;

	public WxAutoresponse() {
	}

	public WxAutoresponse(String id) {
		this.id = id;
	}

	public WxAutoresponse(String id, String accountId, String addTime,
			String keyword, String msgType, String resTemplateId,
			String templateName) {
		this.id = id;
		this.accountId = accountId;
		this.addTime = addTime;
		this.keyword = keyword;
		this.msgType = msgType;
		this.resTemplateId = resTemplateId;
		this.templateName = templateName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAddTime() {
		return this.addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getResTemplateId() {
		return this.resTemplateId;
	}

	public void setResTemplateId(String resTemplateId) {
		this.resTemplateId = resTemplateId;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}

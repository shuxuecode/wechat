package com.weixin.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Generated 2014-7-31 10:55:21 by Hibernate Tools 3.2.2.GA

/**
 * WxAccount generated by hbm2java
 */
@Entity
@Table(name = "wx_account")
public class WxAccount implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="WX_ACCOUNTINFO_ID")
	private WxAccountinfo wxAccountinfo;
	
	@Column(name = "ACCOUNTNAME")
	private String accountname;
	
	@Column(name = "TYPE")
	private Integer type;
	
	@Column(name = "WXID")
	private String wxId;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "BINDING")
	private String binding;
	
	@Column(name = "APPID")
	private String appid;
	
	@Column(name = "SECRET")
	private String secret;
	
	@Column(name = "ACCESS_TOKEN")
	private String accessToken;
	
	@Column(name = "EXPIRES_IN")
	private String expiresIn;
	
	@Column(name = "DEADLINE")
	private String deadline;

	public WxAccount() {
	}

	public WxAccount(String id) {
		this.id = id;
	}

	public WxAccount(String id, WxAccountinfo wxAccountinfo,
			String accountname, Integer type, String wxId, String url,
			String token, String binding, String appid, String secret, String accessToken,
			String expiresIn, String deadline) {
		this.id = id;
		this.wxAccountinfo = wxAccountinfo;
		this.accountname = accountname;
		this.type = type;
		this.wxId = wxId;
		this.url = url;
		this.token = token;
		this.binding = binding;
		this.appid = appid;
		this.secret = secret;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.deadline = deadline;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WxAccountinfo getWxAccountinfo() {
		return this.wxAccountinfo;
	}

	public void setWxAccountinfo(WxAccountinfo wxAccountinfo) {
		this.wxAccountinfo = wxAccountinfo;
	}

	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWxId() {
		return this.wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getDeadline() {
		return this.deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

}

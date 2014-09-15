package com.weixin.web.entity.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Generated 2014-8-27 14:52:41 by Hibernate Tools 3.2.2.GA

/**
 * WxSmart generated by hbm2java
 */
@Entity
@Table(name = "wx_smart")
public class WxSmart implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@Column(name = "accountId")
	private String accountId;
	
	@Column(name = "subscribe")
	private String subscribe;
	
	@Column(name = "subscribe_key")
	private String subscribeKey;
	
	@Column(name = "nomatch")
	private String nomatch;
	
	@Column(name = "nomatch_key")
	private String nomatchKey;
	
	@Column(name = "addTime")
	private String addTime;

	public WxSmart() {
	}

	public WxSmart(String id) {
		this.id = id;
	}

	public WxSmart(String id, String accountId, String subscribe,
			String subscribeKey, String nomatch, String nomatchKey,
			String addTime) {
		this.id = id;
		this.accountId = accountId;
		this.subscribe = subscribe;
		this.subscribeKey = subscribeKey;
		this.nomatch = nomatch;
		this.nomatchKey = nomatchKey;
		this.addTime = addTime;
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

	public String getSubscribe() {
		return this.subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribeKey() {
		return this.subscribeKey;
	}

	public void setSubscribeKey(String subscribeKey) {
		this.subscribeKey = subscribeKey;
	}

	public String getNomatch() {
		return this.nomatch;
	}

	public void setNomatch(String nomatch) {
		this.nomatch = nomatch;
	}

	public String getNomatchKey() {
		return this.nomatchKey;
	}

	public void setNomatchKey(String nomatchKey) {
		this.nomatchKey = nomatchKey;
	}

	public String getAddTime() {
		return this.addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}

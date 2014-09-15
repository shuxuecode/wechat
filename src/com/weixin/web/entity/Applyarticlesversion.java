package com.weixin.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
// Generated 2014-7-3 15:55:50 by Hibernate Tools 3.2.2.GA
import javax.persistence.Table;

/**
 * Applyarticlesversion generated by hbm2java
 */
@Entity
@Table(name = "applyarticlesversion")
public class Applyarticlesversion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID", nullable = false, length = 36)
	private String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "applyId")
	private Articlesapply articlesapply;
	
	@Column(name = "modifyUser")
	private String modifyUser;
	
	@Column(name = "modifyTime")
	private String modifyTime;
	
	@Column(name = "audit")
	private String audit;
	
	@Column(name = "auditAdvise")
	private String auditAdvise;
	
	@Column(name = "remark")
	private String remark;

	public Applyarticlesversion() {
	}

	public Applyarticlesversion(String id) {
		this.id = id;
	}

	public Applyarticlesversion(String id, Articlesapply articlesapply,
			String modifyUser, String modifyTime, String audit,
			String auditAdvise, String remark) {
		this.id = id;
		this.articlesapply = articlesapply;
		this.modifyUser = modifyUser;
		this.modifyTime = modifyTime;
		this.audit = audit;
		this.auditAdvise = auditAdvise;
		this.remark = remark;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Articlesapply getArticlesapply() {
		return this.articlesapply;
	}

	public void setArticlesapply(Articlesapply articlesapply) {
		this.articlesapply = articlesapply;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAudit() {
		return this.audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getAuditAdvise() {
		return this.auditAdvise;
	}

	public void setAuditAdvise(String auditAdvise) {
		this.auditAdvise = auditAdvise;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
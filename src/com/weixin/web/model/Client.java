package com.weixin.web.model;

import java.util.List;

public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	private List<String> privileges;
	/**
	 * 用户IP
	 */
	private String ip;
	/**
	 *登录时间
	 */
	private String logindatetime;
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLogindatetime() {
		return logindatetime;
	}
	public void setLogindatetime(String logindatetime) {
		this.logindatetime = logindatetime;
	}
	
	
	
	
	
	
	

}

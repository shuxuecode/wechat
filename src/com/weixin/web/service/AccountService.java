package com.weixin.web.service;

import java.util.List;

import org.weixin.entity.WxGroup;

import com.weixin.web.entity.WxAccount;
import com.weixin.web.model.Account;
import com.weixin.web.model.json.JsonModel;
/**
 * 公共号service层接口
 * @author zsx
 *
 */

public interface AccountService {
	/**
	 * 根据id获取公共号信息
	 */
	WxAccount getWxAccountById(String id);
	/**
	 * 获取全部的公共号信息——需要改——只有管理员可以
	 */
	List<Account> getAllAccount();
	/**
	 * 根据id获取公共号accessToken
	 */
	String getAccessTokenById(String accountId);
	/**
	 * 根据公共号微信原始id获取账户id
	 */
	String getAccountIdByWxId(String wxid);
	/**
	 * 用户注册
	 */
	JsonModel register(String name, String email, String pwd);
	/**
	 * 保存公共号信息
	 */
	JsonModel addAccountInformation(String accountid, String name, String type, String wxid, String appid, String secret);
	/**
	 * 根据公共号id获取该号的分组信息列表
	 */
	List<WxGroup> getGroupListByAccountId(String accountId);
	
}

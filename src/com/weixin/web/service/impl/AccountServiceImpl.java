package com.weixin.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import myUtil.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.dao.WxGroupDao;
import org.weixin.entity.WxGroup;
import org.weixin.pojo.AccessToken;
import org.weixin.util.WeixinUtil;

import com.weixin.core.constant.Globals;
import com.weixin.core.util.ContextHolderUtils;
import com.weixin.core.util.GetShortUUID;
import com.weixin.core.util.MD5Util;
import com.weixin.web.dao.UserDao;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.dao.WxAccountinfoDao;
import com.weixin.web.entity.Tuser;
import com.weixin.web.entity.WxAccount;
import com.weixin.web.entity.WxAccountinfo;
import com.weixin.web.model.Account;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private WxAccountDao wxAccountDao;
	@Autowired
	private WxAccountinfoDao wxAccountinfoDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private WxGroupDao wxGroupDao;
	
	public WxAccount getWxAccountById(String id){
		return wxAccountDao.get(WxAccount.class, id);
	}
	
	
	public List<Account> getAllAccount(){
		List<Account> list = new ArrayList<Account>();
		
		List<WxAccount> accountList = wxAccountDao.find("from WxAccount w");
		
		for (int i = 0; i < accountList.size(); i++) {
			Account acc = new Account();
			WxAccount account = accountList.get(i);
			BeanUtils.copyProperties(account, acc);
			
			acc.setAccounttype((account.getType() == null) ? "" : ((account.getType() == 0)? "订阅号" : "服务号"));
			
			WxAccountinfo accountinfo = account.getWxAccountinfo();
			acc.setAccountId(account.getId());
			acc.setUserName((accountinfo.getUsername() == null) ? "" : accountinfo.getUsername());
			acc.setPassWord((accountinfo.getPassword() == null) ? "" : accountinfo.getPassword());
			acc.setPhone((accountinfo.getPhone() == null) ? "" : accountinfo.getPhone());
		
			list.add(acc);
		}
		
		return list;
	}


	@Override
	public String getAccessTokenById(String accountId) {
		// TODO Auto-generated method stub
		WxAccount wxAccount = wxAccountDao.get(WxAccount.class, accountId);
		if (wxAccount != null) {
			long deadline = Long.valueOf(wxAccount.getDeadline());
			if (System.currentTimeMillis() > deadline) {
				AccessToken at = WeixinUtil.getAccessToken(
						wxAccount.getAppid(), wxAccount.getSecret());
				wxAccount.setAccessToken(at.getToken());
				wxAccount.setDeadline(Long
						.toString(System.currentTimeMillis() + 1000 * 7000));
				wxAccountDao.update(wxAccount);
				return at.getToken();
			} else {
				return wxAccount.getAccessToken();
			}
		}
		return null;
	}


	@Override
	public String getAccountIdByWxId(String wxid) {
		// TODO Auto-generated method stub
		WxAccount wxAccount = wxAccountDao.get("from WxAccount w where w.wxId = '" + wxid + "'");
		if (wxAccount != null) {
			return wxAccount.getId();
		}else {
			return null;
		}
	}


	@Override
	public JsonModel register(String name, String email, String pwd) {
		JsonModel jsonModel = new JsonModel();
		try {
			pwd = MD5Util.md5(pwd);//加密
			
			WxAccountinfo wxAccountinfo = new WxAccountinfo();
			wxAccountinfo.setId(UUID.randomUUID().toString());
			wxAccountinfo.setUsername(name);
			wxAccountinfo.setEmail(email);
			wxAccountinfo.setPassword(pwd);
			wxAccountinfo.setRegistTime(DateUtil.timeFormat());
			
			wxAccountinfoDao.save(wxAccountinfo);
			
			WxAccount wxAccount = new WxAccount();
			wxAccount.setId(UUID.randomUUID().toString());
			wxAccount.setWxAccountinfo(wxAccountinfo);
			
			wxAccountDao.save(wxAccount);
			
			Tuser user = new Tuser();
			user.setId(wxAccount.getId());
			user.setUserName(name);
			user.setPassWord(pwd);
			
			userDao.save(user);
			
			//注册的用户没有任何权限，需要分配角色
			
			jsonModel.setSuccess(true);
			jsonModel.setMsg(user.getId());
		} catch (Exception e) {
			jsonModel.setSuccess(false);
			jsonModel.setMsg("");
		}
		return jsonModel;
	}


	@Override
	public JsonModel addAccountInformation(String accountid, String name,
			String type, String wxid, String appid, String secret) {
		JsonModel jModel = new JsonModel();
		WxAccount account = wxAccountDao.get(WxAccount.class, accountid);
		if (account != null) {
			account.setAccountname(name);
			account.setType(Integer.valueOf(type));
			//配置服务器接口
			String oldWxid = account.getWxId();
			if (oldWxid == null || oldWxid == "" || !oldWxid.equals(wxid)) {
				account.setWxId(wxid);
				account.setUrl(ContextHolderUtils.getBasePath()+"validate/" + wxid);
				account.setToken(GetShortUUID.generateShortUuid());
			}
			account.setAppid(appid);
			account.setSecret(secret);
			
			wxAccountDao.update(account);
			jModel.setSuccess(true);
			jModel.setMsg("修改成功");
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("修改失败");
		}
		return jModel;
	}


	@Override
	public List<WxGroup> getGroupListByAccountId(String accountId){
		return wxGroupDao.find("from WxGroup w where w.accountid = '" + accountId + "'");
	}
	
	
}

package com.weixin.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin.entity.WxGroup;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixin.core.util.JsonUtil;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.entity.WxAccount;
import com.weixin.web.model.Account;
import com.weixin.web.model.DataGrid;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.AccountService;

/**
 * 公众号管理
 * @author zhao
 *
 */

@Controller
@RequestMapping("account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	/**
	 * 账户管理模块
	 * @return
	 */
	@RequestMapping("toAccountManager")
	public String toAccountManager(){
		return "jsp/account/accountManager";
	}
	/**
	 * 跳转到公共号管理
	 * @return
	 */
	@RequestMapping("toaccountShow")
	public String toaccountShow(){
		return "jsp/account/accountShow";
	}
	/**
	 * 跳转到角色管理
	 * @return
	 */
	@RequestMapping("toroleShow")
	public String toroleShow(){
		return "jsp/account/roleShow";
	}
	/**
	 * 跳转到权限管理
	 * @return
	 */
	@RequestMapping("toprivilegeShow")
	public String toprivilegeShow(){
		return "jsp/account/privilegeShow";
	}
	/**
	 * 账户列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("datagrid")
	@ResponseBody
	public void dataGrid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		List<Account> list = accountService.getAllAccount();
		
		DataGrid dGrid = new DataGrid();
		dGrid.setTotal(list.size());
		dGrid.setRows(list);
		
		JsonUtil.objectToJson(dGrid, response);
		
	}
	/**
	 * 获取公共号信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getInformation")
	public String accountInformation(HttpServletRequest request, HttpServletResponse response){
		String accountid = (String) request.getSession().getAttribute("accountId");
		WxAccount wxa = accountService.getWxAccountById(accountid);
		request.setAttribute("account", wxa);
		return "jsp/system/information";
	} 
	/**
	 * 修改公共号信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("updateInformation")
	public String updateInformation(HttpServletRequest request, HttpServletResponse response){
		String accountid = (String) request.getSession().getAttribute("accountId");
		WxAccount wxa = accountService.getWxAccountById(accountid);
		request.setAttribute("account", wxa);
		return "jsp/system/informationUpdate";
	} 
	/**
	 * 保存公共号信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("saveInformation")
	@ResponseBody
	public void addqrcode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String wxid = request.getParameter("wxid");
		String appid = request.getParameter("appid");
		String secret = request.getParameter("secret");
		
		String accountid = (String) request.getSession().getAttribute("accountId");
		
		JsonModel jm = accountService.addAccountInformation(accountid, name, type, wxid, appid, secret);
		JsonUtil.objectToJson(jm, response);
	}
	
	/**
	 * 获取公共号服务器配置信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getInterface")
	public String getInterface(HttpServletRequest request, HttpServletResponse response){
		String accountid = (String) request.getSession().getAttribute("accountId");
		WxAccount wxa = accountService.getWxAccountById(accountid);
		request.setAttribute("url", (wxa.getUrl() == null) ? "" : wxa.getUrl());
		request.setAttribute("token", (wxa.getToken() == null) ? "" : wxa.getToken());
		request.setAttribute("binding", wxa.getBinding());
		
		return "jsp/system/interface";
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

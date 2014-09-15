package com.weixin.web.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myUtil.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixin.core.manager.ClientManager;
import com.weixin.core.util.ContextHolderUtils;
import com.weixin.core.util.IpUtil;
import com.weixin.core.util.JsonUtil;
import com.weixin.core.util.MD5Util;
import com.weixin.web.dao.TprivilegeDao;
import com.weixin.web.dao.TroleDao;
import com.weixin.web.entity.Tprivilege;
import com.weixin.web.entity.Trole;
import com.weixin.web.model.Client;
import com.weixin.web.model.DataGrid;
import com.weixin.web.model.Tree;
import com.weixin.web.model.User;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.UserService;
/**
 * 用户
 * @author zsx
 *
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	/**
	 * 跳转到用户管理模块
	 */
	@RequestMapping("toUserManager")
	public String toUserManager(){
		return "jsp/user/userManager";
	}
	/**
	 * 跳转到用户列表页面
	 */
	@RequestMapping("touserShow")
	public String touserShow(){
		return "jsp/user/userShow";
	}
	/**
	 * 添加用户
	 */
//	@RequestMapping("/addUser")
//	@ResponseBody
//	public void addUser(User user, HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		// 也可以按对象接受参数
//		JsonModel jModel = userService.addUser(user);
//		JsonUtil.objectToJson(jModel, response);
//
//	}

	/**
	 * 获取用户列表
	 */
	@RequestMapping("/getUserList")
	public void getJson(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<User> list = userService.getAllUser();
		DataGrid dg = new DataGrid();
		dg.setTotal(list.size());
		dg.setRows(list);
		JsonUtil.objectToGsonJson(dg, true, response);
	}
	/**
	 * 获取角色列表
	 */
	@RequestMapping("getRoleList")
	@ResponseBody
	public void getRoleList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		List<Trole> role = userService.getAllRole();
		if (role != null && role.size() > 0) {
			List<Trole> list = new ArrayList<Trole>();
			for (Trole trole : role) {
				Trole r = new Trole();
				r.setId(trole.getId());
				r.setRoleName(trole.getRoleName());
				r.setRoleNumber(trole.getRoleNumber());
				
				list.add(r);
			}
			DataGrid dg = new DataGrid();
			dg.setTotal(list.size());
			dg.setRows(list );
			
			JsonUtil.objectToJson(dg, response);
		}
	}
	
	/**
	 * 获取角色Tree
	 */
	@RequestMapping("getRoleTree")
	@ResponseBody
	public void getRoleTree(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userId = request.getParameter("userId");
		List<Tree> role = userService.getUserRoleByUserId(userId);
		JsonUtil.objectToJson(role, response);
	}
	
	/**
	 * 修改用户角色
	 */
	@RequestMapping("updUserRole")
	@ResponseBody
	public void updUserRole(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userId = request.getParameter("userId");
		String roleIds = request.getParameter("roleIds");
		JsonModel json = userService.updateUserRole(userId, roleIds);
		JsonUtil.objectToJson(json, response);
	}
	
	
	/**
	 * 添加角色
	 */
	@RequestMapping("addRole")
	@ResponseBody
	public void addRole(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String name = request.getParameter("name");
		String num = request.getParameter("num");
		JsonModel jModel = userService.addrole(name, num);
		JsonUtil.objectToJson(jModel, response);
	}
	/**
	 * 修改角色
	 */
	@RequestMapping("updRole")
	@ResponseBody
	public void updRole(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String num = request.getParameter("num");
		JsonModel jModel = userService.updaterole(id, name, num);
		JsonUtil.objectToJson(jModel, response);
	}
	/**
	 * 删除角色
	 */
	@RequestMapping("delRole")
	@ResponseBody
	public void delRole(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		JsonModel jModel = userService.deleterole(id);
		JsonUtil.objectToJson(jModel, response);
	}
	
	/**
	 * 获取权限列表
	 */
	@RequestMapping("getPrivilegeTree")
	@ResponseBody
	public void getPrivilegeTree(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Tree> tree = userService.getAllPrivilegeTree();
		JsonUtil.objectToJson(tree, response);
	}
	/**
	 * 根据角色——获取角色权限列表
	 */
	@RequestMapping("getRolePrivilegeTree")
	@ResponseBody
	public void getRolePrivilegeTree(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String roleId = request.getParameter("roleId");
		List<Tree> tree = userService.getPrivilegeTreeByRoleId(roleId);
		JsonUtil.objectToJson(tree, response);
	}
	/**
	 * 修改角色权限
	 */
	@RequestMapping("updateRolePrivilege")
	@ResponseBody
	public void updateRolePrivilege(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String roleId = request.getParameter("roleId");
		String ids = request.getParameter("ids");
		JsonModel jModel = userService.updateRolePrivileges(roleId, ids);
		JsonUtil.objectToJson(jModel, response);
	}
	/**
	 * 获取权限树——父节点
	 */
	@RequestMapping("getParentPrivilegeTree")
	@ResponseBody
	public void getParentPrivilegeTree(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Tree> tree = userService.getPrivilegeParentTree();
		JsonUtil.objectToJson(tree, response);
	}
	/**
	 * 添加权限
	 */
	@RequestMapping("addPrivilege")
	@ResponseBody
	public void addPrivilege(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String name = request.getParameter("add_name");
		String url = request.getParameter("add_url");
		String pid = request.getParameter("add_pid");
		
		JsonModel jModel = userService.addprivilege(name, url, pid);
		JsonUtil.objectToJson(jModel, response);
	}
	/**
	 * 修改权限
	 */
	@RequestMapping("updPrivilege")
	@ResponseBody
	public void updPrivilege(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("upd_id");
		String name = request.getParameter("upd_name");
		String url = request.getParameter("upd_url");
		String pid = request.getParameter("upd_pid");
		
		JsonModel jModel = userService.updateprivilege(id, name, url, pid);
		JsonUtil.objectToJson(jModel, response);
	}
	/**
	 * 删除权限
	 */
	@RequestMapping("delPrivilege")
	@ResponseBody
	public void delPrivilege(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		JsonModel jModel = userService.deleteprivilege(id);
		JsonUtil.objectToJson(jModel, response);
	}

}

package com.weixin.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixin.core.constant.Globals;
import com.weixin.core.util.JsonUtil;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.model.Tree;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.MenuService;
import com.weixin.web.service.MsgService;
/**
 * 菜单
 * @author zsx
 *
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	@Autowired
	private MsgService msgService;
	
	
	/**
	 * 跳转到菜单管理模块
	 */
	@RequestMapping("toMenuManager")
	public String toMenuManager(){
		return "jsp/menu/menuManager"; 
	}
	/**
	 * 跳转到我的菜单
	 */
	@RequestMapping("toMyMenu")
	public String toMyMenu(){
		return "jsp/menu/MyMenu";
	}
	/**
	 * 显示主菜单页面
	 */
	@RequestMapping("toMainMenuTable")
	public String toMainMenuTable(){
		return "jsp/menu/mainMenuTable";
	}
	/**
	 * 显示子菜单页面
	 */
	@RequestMapping("tosubMenuTable")
	public String tosubMenuTable(){
		return "jsp/menu/subMenuTable";
	}
	/**
	 * 加载我的菜单显示页面
	 */
	@RequestMapping("/mymenu")
	public String getJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Tree> mainMenuList = new ArrayList<Tree>();

		String userId = (String) request.getSession().getAttribute("accountId");

		List<WXmenu> list = menuService.getMenuByUser(userId, "");

		if (list != null) {
			for (WXmenu menu : list) {
				if (menu.getWxMenu() == null) {
					Tree tree = new Tree();
					tree.setId(menu.getId());
					tree.setText(menu.getName());

					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("type", menu.getMenuType());
					if (menu.getMenuType() == Globals.MENUTYPE_NULL) {

					} else if (menu.getMenuType() == Globals.MENUTYPE_KEYWORD) {
						//获取关键词
						attributes.put("keyWord", msgService.getKeywordById(menu.getKeyWord()));
						attributes.put("keyWordId", menu.getKeyWord());
					} else if (menu.getMenuType() == Globals.MENUTYPE_WEBSITE) {
						attributes.put("webName", menu.getWebsite());
						attributes.put("webUrl", menu.getWebsite());
					} else if (menu.getMenuType() == Globals.MENUTYPE_URL) {
						attributes.put("url", menu.getUrl());
					}

					tree.setAttributes(attributes);
					//查询子菜单
					List<WXmenu> subMenus = menuService.getSubMenuByPID(menu
							.getId());
					if (subMenus != null && subMenus.size() > 0) {
						List<Tree> subMenuList = new ArrayList<Tree>();
						for (WXmenu m : subMenus) {
							Tree t = new Tree();
							t.setId(m.getId());
							t.setText(m.getName());

							Map<String, Object> attr = new HashMap<String, Object>();
							attr.put("type", m.getMenuType());
							if (m.getMenuType() == Globals.MENUTYPE_NULL) {

							} else if (m.getMenuType() == Globals.MENUTYPE_KEYWORD) {
								attr.put("keyWord", msgService.getKeywordById(m.getKeyWord()));
								attr.put("keyWordId", m.getKeyWord());
							} else if (m.getMenuType() == Globals.MENUTYPE_WEBSITE) {
								attr.put("webName", m.getWebsite());
								attr.put("webUrl", m.getWebsite());
							} else if (m.getMenuType() == Globals.MENUTYPE_URL) {
								attr.put("url", m.getUrl());
							}

							t.setAttributes(attr);

							subMenuList.add(t);
						}
						// 子菜单
						map.put(tree.getId(), subMenuList);
						//
						tree.setState("open");
					}else {
						tree.setState("closed");
					}

					// 主菜单
					mainMenuList.add(tree);
				}
			}
		}
		map.put("list", mainMenuList);
		request.setAttribute("map", map);
		

		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(map));
		return "/jsp/menu/MenuBtn";
	}
	/**
	 * 修改我的菜单
	 */
	@RequestMapping("/update")
	@ResponseBody
	public void updateMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String name = URLDecoder.decode(request.getParameter("name"), "UTF-8");
		String menuType = request.getParameter("menuType");
		String keyWord = request.getParameter("keyWord");
		String webSite = request.getParameter("webSite");
		String url = request.getParameter("url");

		JsonModel jsonModel = menuService.updateMenu(id, name, menuType, keyWord, webSite, url);
		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 添加菜单
	 */
	@RequestMapping("/add")
	@ResponseBody
	public void addMenu(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String userId = (String) request.getSession().getAttribute("accountId");

		// String name = URLDecoder.decode(request.getParameter("name"),
		// "UTF-8");
		String pid = request.getParameter("pid");
		String name = request.getParameter("name");
		String menuType = request.getParameter("menuType");
		String keyWord = request.getParameter("keyWord");
		String webSite = request.getParameter("webSite");
		String url = request.getParameter("url");
		
		JsonModel json = menuService.addMenu(userId, name, menuType, keyWord, webSite, url, pid);
		JsonUtil.objectToJson(json, response);

	}
	/**
	 * 删除菜单
	 */
	@RequestMapping("delmenu")
	@ResponseBody
	public void delMenu(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		JsonModel jsonModel = menuService.delMenu(id);

		JsonUtil.objectToJson(jsonModel, response);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

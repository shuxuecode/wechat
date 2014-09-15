package com.weixin.web.service;

import java.util.List;

import com.weixin.web.entity.WXmenu;
import com.weixin.web.model.json.JsonModel;
/**
 * 菜单接口
 * @author zsx
 *
 */
public interface MenuService {
	/**
	 * 根据账户id获取菜单信息
	 */
	List<WXmenu> getMenuByUser(String accountId, String menuType);
	/**
	 * 根据主菜单id获取子菜单列表
	 */
	List<WXmenu> getSubMenuByPID(String pid);
	/**
	 * 修改菜单
	 */
	JsonModel updateMenu(String id, String name, String menuType, String keyWord,
			String webSite, String url);
	/**
	 * 添加菜单
	 */
	JsonModel addMenu(String userId, String name, String menuType, String keyWord,
			String webSite, String url, String pid);
	/**
	 * 删除菜单
	 */
	JsonModel delMenu(String id);
}

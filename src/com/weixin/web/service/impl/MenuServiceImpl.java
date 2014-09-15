package com.weixin.web.service.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.swing.JMenu;

import myUtil.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.core.constant.Globals;
import com.weixin.core.util.GetShortUUID;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private WXmenuDao wXmenuDao;

	public List<WXmenu> getMenuByUser(String accountId, String menuType){

		List<WXmenu> list = wXmenuDao.find("from WXmenu w where w.accountId ='"
				+ accountId + "' and w.wxMenu is " + menuType + " null");
		
		if (list.size() > 0) {

			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<WXmenu> getSubMenuByPID(String pid) {
		// TODO Auto-generated method stub
		List<WXmenu> list = wXmenuDao.find("from WXmenu w where w.wxMenu.id ='" + pid + "'");
		if (list.size() > 0) {

			return list;
		} else {
			return null;
		}
	}

	@Override
	public JsonModel updateMenu(String id, String name, String menuType, String keyWord,
			String webSite, String url) {
		JsonModel json = new JsonModel();
		try {
			WXmenu wXmenu = wXmenuDao.get(WXmenu.class, id);
			if (wXmenu != null) {
				wXmenu.setName(name);
				wXmenu.setModifyTime(DateUtil.timeFormat());
				
				int type = Integer.valueOf(menuType);
				wXmenu.setMenuType(type);
				if (type == Globals.MENUTYPE_NULL) {
				}else if (type == Globals.MENUTYPE_KEYWORD) {
					wXmenu.setKeyWord(keyWord);
					wXmenu.setType(Globals.MENU_TYPE_CLICK);
				}else if (type == Globals.MENUTYPE_WEBSITE) {
					wXmenu.setWebsite(webSite);
					wXmenu.setType(Globals.MENU_TYPE_CLICK);
				}else if (type == Globals.MENUTYPE_URL) {
					wXmenu.setUrl(url);
					wXmenu.setType(Globals.MENU_TYPE_VIEW);
				}
				
				wXmenuDao.update(wXmenu);
				json.setSuccess(true);
				json.setMsg("修改成功");
			}else {
				json.setSuccess(false);
				json.setMsg("修改失败");
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("修改失败");
		}
		return json;
	}


	@Override
	public JsonModel addMenu(String userId, String name, String menuType,
			String keyWord, String webSite, String url, String pid) {
		JsonModel json = new JsonModel();
		try {
			WXmenu wXmenu = new WXmenu();
			wXmenu.setId(UUID.randomUUID().toString());
			wXmenu.setName(name);
			wXmenu.setAccountId(userId);
			wXmenu.setCreateTime(DateUtil.timeFormat());
			wXmenu.setMenuKey(GetShortUUID.generateShortUuid());//指定一个key
			
			int type = Integer.valueOf(menuType);
			wXmenu.setMenuType(type);
			if (type == Globals.MENUTYPE_NULL) {
			}else if (type == Globals.MENUTYPE_KEYWORD) {
				wXmenu.setKeyWord(keyWord);
				wXmenu.setType(Globals.MENU_TYPE_CLICK);
			}else if (type == Globals.MENUTYPE_WEBSITE) {
				wXmenu.setWebsite(webSite);
				wXmenu.setType(Globals.MENU_TYPE_CLICK);
			}else if (type == Globals.MENUTYPE_URL) {
				wXmenu.setUrl(url);
				wXmenu.setType(Globals.MENU_TYPE_VIEW);
			}
			
			if (!StringUtils.equals("", pid)) {
				WXmenu wx = wXmenuDao.get(WXmenu.class, pid);
				if(wx != null){
					wXmenu.setWxMenu(wx);
				}
			}
			//顺序暂时没做，需要重新定义
			wXmenu.setOrders(1);//
			
			wXmenuDao.save(wXmenu);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("添加失败");
		}
		return json;
	}

	@Override
	public JsonModel delMenu(String id) {
		JsonModel jModel = new JsonModel();
		WXmenu menu = wXmenuDao.get(WXmenu.class, id);
		if (menu != null) {
			Set<WXmenu> set = menu.getWxMenus();
			if (set.size() > 0) {
				for (WXmenu xmenu : set) {
					wXmenuDao.delete(xmenu);
				}
			}
			wXmenuDao.delete(menu);
			
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}


}

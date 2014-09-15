package org.weixin.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.pojo.AccessToken;
import org.weixin.pojo.Button;
import org.weixin.pojo.CommonButton;
import org.weixin.pojo.ComplexButton;
import org.weixin.pojo.Menu;
import org.weixin.pojo.ViewButton;
import org.weixin.service.WeixinMenuService;
import org.weixin.util.WeixinUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.entity.WxAccount;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.AccountService;

@Service
@Transactional
public class WeixinMenuServiceImpl implements WeixinMenuService {
	
	@Autowired
	private WXmenuDao wXmenuDao;

	@Autowired
	private WxAccountDao wxAccountDao;
	
	@Autowired
	private AccountService accountService;

	public JsonModel sync(String accountId) throws IOException {
		JsonModel jModel = new JsonModel();

		// 获取主菜单
		List<WXmenu> mainMenu = wXmenuDao
				.find("from WXmenu w where w.accountId = '" + accountId
						+ "' and w.wxMenu = null order by w.orders ");
		if (mainMenu.size() > 0) {
			// 创建按钮组合
			Button button[] = new Button[mainMenu.size()];
			for (int i = 0; i < mainMenu.size(); i++) {
				WXmenu xmenu = mainMenu.get(i);
				// 获取子菜单
				List<WXmenu> subMenu = wXmenuDao
						.find("from WXmenu w where w.wxMenu.id = '"
								+ xmenu.getId() + "' order by w.orders ");
				// 如果没有子菜单
				if (subMenu.size() == 0) {
					if ("click".equals(xmenu.getType())) {
						CommonButton cb = new CommonButton();
						cb.setName(xmenu.getName());
						cb.setType(xmenu.getType());
						cb.setKey(xmenu.getMenuKey());
						button[i] = cb;
					} else if ("view".equals(xmenu.getType())) {
						ViewButton viewButton = new ViewButton();
						viewButton.setName(xmenu.getName());
						viewButton.setType(xmenu.getType());
						viewButton.setUrl(xmenu.getUrl());
						button[i] = viewButton;
					}

				} else {
					// 有子菜单
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(xmenu.getName());

					Button[] subbutton = new Button[subMenu.size()];

					for (int j = 0; j < subMenu.size(); j++) {
						WXmenu xmenu2 = subMenu.get(j);

						if ("click".equals(xmenu2.getType())) {
							CommonButton cb2 = new CommonButton();
							cb2.setName(xmenu2.getName());
							cb2.setType(xmenu2.getType());
							cb2.setKey(xmenu2.getMenuKey());
							subbutton[j] = cb2;
						} else if ("view".equals(xmenu2.getType())) {
							ViewButton vb = new ViewButton();
							vb.setName(xmenu2.getName());
							vb.setType(xmenu2.getType());
							vb.setUrl(xmenu2.getUrl());
							subbutton[j] = vb;
						}
					}
					// 添加子菜单
					complexButton.setSub_button(subbutton);
					button[i] = complexButton;

				}
			}
			Menu menu = new Menu();
			menu.setButton(button);

			System.out.println(JSON.toJSONString(menu));

			String accessToken = accountService.getAccessTokenById(accountId);
			String url = WeixinUtil.menu_create_url.replace("ACCESS_TOKEN",
					accessToken);
			JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", JSON
					.toJSONString(menu));
			System.out.println(jsonObject);
			// 判断菜单创建结果
			if (null != jsonObject) {
				if (0 == jsonObject.getIntValue("errcode")) {
					jModel.setSuccess(true);
					jModel.setMsg("同步菜单信息数据成功！");
				}else {
					jModel.setSuccess(false);
					jModel.setMsg("出错了，错误码为：" + jsonObject.getIntValue("errcode"));
				}
			} else {
				jModel.setSuccess(false);
				jModel.setMsg("同步菜单信息数据失败！");
			}
		} else {
			jModel.setSuccess(false);
			jModel.setMsg("您还没有创建菜单，请先创建菜单！");
		}
		return jModel;
	}
}

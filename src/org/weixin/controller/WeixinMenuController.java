package org.weixin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixin.core.util.JsonUtil;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.entity.WxAccount;
import com.weixin.web.model.json.JsonModel;

@Controller
@RequestMapping("/menuController")
public class WeixinMenuController {

	@Autowired
	private WeixinMenuService weixinMenuService;
	
	
	@RequestMapping("/sync")
	@ResponseBody
	public void sync(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String accountId = (String) request.getSession().getAttribute("accountId");
		JsonModel jm = weixinMenuService.sync(accountId);
		JsonUtil.objectToJson(jm, response);
	}
	
	
	
	
}

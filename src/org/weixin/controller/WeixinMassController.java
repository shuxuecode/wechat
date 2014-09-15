package org.weixin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin.service.WeixinMassService;

import com.weixin.core.constant.Globals;
import com.weixin.core.util.JsonUtil;
import com.weixin.web.service.QrcodeService;
/**
 * 微信群发消息高级接口
 * @author zsx
 *
 */
@Controller
@RequestMapping("mass")
public class WeixinMassController {
	
	@Autowired
	private WeixinMassService weixinMassService;
	@Autowired
	private QrcodeService qrcodeService;

	/**
	 * 按分组群发
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("groupsend")
	@ResponseBody
	public void sendByGroup(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String accountId = (String) request.getSession().getAttribute("accountId");
		String massId = request.getParameter("massid");
		String groupId = request.getParameter("groupid");
		String result = weixinMassService.mass(massId, accountId, Globals.SEND_BY_GROUP, groupId, null);
		
		JsonUtil.objectToJson(result , response);
	}
	/**
	 * 按用户Openid 群发消息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("openidsend")
	@ResponseBody
	public void sendByOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String accountId = (String) request.getSession().getAttribute("accountId");
		String massId = request.getParameter("massid");
		String ids = request.getParameter("ids");
		List<String> openidList = new ArrayList<String>();
		String[] openids = ids.split(",");
		for (int i = 0; i < openids.length; i++) {
			openidList.add(qrcodeService.getOpenIdByUserinfoId(openids[i]));
		}
		if (openidList.size() > 0) {
			String result = weixinMassService.mass(massId, accountId, Globals.SEND_BY_OPENID, null, openidList);
			JsonUtil.objectToJson(result , response);
		}else {
			JsonUtil.objectToJson("您没有选择用户，请重新选择" , response);
		}
	}
	
}

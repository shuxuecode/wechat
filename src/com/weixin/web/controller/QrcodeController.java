package com.weixin.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.weixin.entity.WxUserinfo;

import com.weixin.core.constant.Globals;
import com.weixin.core.util.JsonUtil;
import com.weixin.web.entity.WxQrcode;
import com.weixin.web.entity.message.WxNewsitem;
import com.weixin.web.model.DataGrid;
import com.weixin.web.model.Userinfo;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.QrcodeService;

/**
 * 二维码
 * @author zhao
 *
 */

@Controller
@RequestMapping("qrcode")
public class QrcodeController {

	@Autowired
	private QrcodeService qrcodeService;
	/**
	 * 跳转到二维码模块
	 */
	@RequestMapping("toQrcodeManager")
	public String toQrcodeManager(){
		return "jsp/qrcode/qrcodeManager";
	}
	/**
	 * 跳转到二维码列表
	 */
	@RequestMapping("toqrcodeList")
	public String qrcodeList(){
		return "jsp/qrcode/qrcodeList";
	}
	/**
	 * 跳转到关注用户列表
	 */
	@RequestMapping("touserinfoList")
	public String userinfoList(){
		return "jsp/qrcode/userinfoList";
	}
	/**
	 * 二维码列表
	 * @return
	 */
	@RequestMapping("qrcodeDatagrid")
	@ResponseBody
	public void texttemplateDatagrid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<WxQrcode> list = qrcodeService.getAllQrcode();
		for (WxQrcode wxQrcode : list) {
			if (wxQrcode.getActionName().equals(Globals.QR_LIMIT_SCENE)) {
				wxQrcode.setActionName("yes");
			}else {
				wxQrcode.setActionName("no");
			}
		}
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);
		JsonUtil.objectToJson(dataGrid, response);
	}
	/**
	 * 删除二维码
	 */
	@RequestMapping("delqrcode")
	@ResponseBody
	public void delqrcode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String codeId = request.getParameter("id");
		JsonModel jm = qrcodeService.delWxqrcode(codeId);
		JsonUtil.objectToJson(jm, response);
	}
	
	
//	@RequestMapping("showQRcode")
//	public String toShowMessage(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String id = request.getParameter("id");
//		String ticket = qrcodeService.getTicketById(id);
//		String qrcodeurl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "UTF-8");
//		request.setAttribute("qrcodeurl", qrcodeurl);
//		return "/jsp/qrcode/showQRcode";
//	}
	/**
	 * 添加二维码
	 */
	@RequestMapping("addqrcode")
	@ResponseBody
	public void addqrcode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String params = request.getParameter("params");
		//选中时 值为on ， 不选 为null
		String action = request.getParameter("action");
		
		JsonModel jm = qrcodeService.addWxQrcode(params, "1", action);
		
		JsonUtil.objectToJson(jm, response);
	}
	
	/**
	 * 关注者列表——改为按accountid获取
	 */
	@RequestMapping("userinfoDatagrid")
	@ResponseBody
	public void userinfoDatagrid(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<Userinfo> list = qrcodeService.getAllWxUserinfo();
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);
		JsonUtil.objectToJson(dataGrid, response);
	}
	
	

}

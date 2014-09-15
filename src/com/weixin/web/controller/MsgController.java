package com.weixin.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.weixin.entity.WxGroup;

import com.weixin.core.util.JsonUtil;
import com.weixin.web.entity.message.WxAutoresponse;
import com.weixin.web.entity.message.WxMass;
import com.weixin.web.entity.message.WxNewsitem;
import com.weixin.web.entity.message.WxNewstemplate;
import com.weixin.web.entity.message.WxSmart;
import com.weixin.web.entity.message.WxTexttemplate;
import com.weixin.web.model.ApplyMsg;
import com.weixin.web.model.ArticleModel;
import com.weixin.web.model.ComboboxModel;
import com.weixin.web.model.DataGrid;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.AccountService;
import com.weixin.web.service.MsgService;
/**
 * 消息管理 
 * @author zsx
 *
 */
@Controller
@RequestMapping("/msg")
public class MsgController {

	@Autowired
	private MsgService msgService;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 跳转到消息管理模块
	 */
	@RequestMapping("toMsgManager")
	public String toMsgManager(){
		return "jsp/message/msgManager";
	}
	/**
	 * 跳转到消息列表
	 */
	@RequestMapping("tomsgShow")
	public String tomsgShow(){
		return "jsp/message/msgShow";
	}
	
	/**
	 * 跳转到自动回复消息列表(关键字管理)
	 */
	@RequestMapping("toautoresponseList")
	public String toautoresponseList(){
		return "jsp/message/autoresponseList";
	}
	/**
	 * 跳转到群发消息列表
	 */
	@RequestMapping("tomassList")
	public String tomassList(){
		return "jsp/mass/massList";
	}
	/**
	 * 跳转到分组
	 */
	@RequestMapping("togroupList")
	public String togroupList(){
		return "jsp/mass/groupList";
	}
	/**
	 * 跳转到文本消息列表
	 */
	@RequestMapping("totextTemplateList")
	public String textTemplateList(){
		return "jsp/textTemplate/textTemplateList";
	}
	/**
	 * 跳转到图文消息列表
	 */
	@RequestMapping("tonewsTemplateList")
	public String newsTemplateList(){
		return "jsp/newsTemplate/newsTemplateList";
	}
	/**
	 * 我的消息列表
	 */
	@RequestMapping("/getMsgList")
	public void getJson(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String status = request.getParameter("status");
		String userId = (String) request.getSession().getAttribute("accountId");

		List<ApplyMsg> list = msgService.getMyMsgByUser(userId, Integer
				.valueOf(status));
		
		JsonUtil.objectToJson(list, response);
	}
	/**
	 * 提交申请
	 */
	@RequestMapping("/msgSubmit")
	@ResponseBody
	public void submitTask(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String userId = (String) request.getSession().getAttribute("accountId");

		JsonModel json = msgService.submitTask(id, userId);
		JsonUtil.objectToJson(json, response);
	}
	/**
	 * 驳回申请
	 */
	@RequestMapping("/refuse")
	@ResponseBody
	public void refuse(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String id = request.getParameter("id");
		String userId = (String) request.getSession().getAttribute("accountId");

		JsonModel json = msgService.refuse(id, userId);
		JsonUtil.objectToJson(json, response);
	}
	/**
	 * 添加消息
	 */
	@RequestMapping("/addMsg")
	@ResponseBody
	public void addUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String title = request.getParameter("title");
		String msgType = request.getParameter("msgType");
		String content = request.getParameter("content");

		String userId = (String) request.getSession().getAttribute("accountId");
		// 也可以按对象接受参数
		JsonModel json = msgService.add(title, msgType, content, userId);
		JsonUtil.objectToJson(json, response);
	}
	/**
	 * 文本消息列表
	 */
	@RequestMapping("texttemplateDatagrid")
	@ResponseBody
	public void texttemplateDatagrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		List<WxTexttemplate> list = msgService.getAllTextTemplate();

		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);

		JsonUtil.objectToJson(dataGrid, response);
	}
	/**
	 * 删除文本消息
	 */
	@RequestMapping("delTexttemplate")
	@ResponseBody
	public void delTexttemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tempId = request.getParameter("id");

		JsonModel jsonModel = msgService.delTextTemplate(tempId);

		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 添加文本消息
	 */
	@RequestMapping("addTexttemplate")
	@ResponseBody
	public void addTexttemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("title");
		String content = request.getParameter("content");

		JsonModel jsonModel = msgService.addTextTemplate(name, content);

		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 图文消息列表
	 */
	@RequestMapping("newstemplateDatagrid")
	@ResponseBody
	public void newstemplateDatagrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<WxNewstemplate> list = msgService.getAllNewsTemplate();

		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);

		JsonUtil.objectToJson(dataGrid, response);
	}
	/**
	 * 添加图文消息
	 */
	@RequestMapping("addNewstemplate")
	@ResponseBody
	public void addNewstemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String name = request.getParameter("tempName");

		JsonModel jsonModel = msgService.addNewsTemplate(name);

		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 图文消息展示
	 */
	@RequestMapping("showMessage")
	public String toShowMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tempId = request.getParameter("id");
		String date = request.getParameter("date");
		List<WxNewsitem> list = msgService.getWxNewsitemsBytempId(tempId);

		request.setAttribute("list", list);
		request.setAttribute("date", date);
		return "/jsp/newsTemplate/showMessage";
	}
	/**
	 * 添加单条图文
	 */
	@RequestMapping("addWxNewsitem")
	@ResponseBody
	public void addWxNewsitem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String content = request.getParameter("content");
		String url = request.getParameter("url");
		String sort = request.getParameter("sort");
		String templateId = request.getParameter("tempId");

		String accountId = "";

		InputStream input = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile = multipartRequest.getFile("coverimg");
		String filename = mfile.getOriginalFilename();
		try {
			input = mfile.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JsonModel jsonModel = msgService.addNewsitem(title, author,
				description, content, input, filename, url, sort, templateId,
				accountId);
		
		JsonUtil.objectToJson(jsonModel, response);

	}
	/**
	 * 跳转到修改单条图文页面
	 */
	@RequestMapping("updateNewsitem")
	public String updateNewsitem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		WxNewsitem wxn = msgService.getWxNewsitemById(id);
		if (wxn != null) {
			request.setAttribute("id", wxn.getId());
			request.setAttribute("title", wxn.getTitle());
			request.setAttribute("author", wxn.getAuthor());
			request.setAttribute("coverimg", wxn.getPicUrl());
			request.setAttribute("description", wxn.getDescription());
			request.setAttribute("content", wxn.getContent());
			request.setAttribute("url", wxn.getUrl());
			request.setAttribute("sort", wxn.getSort());
		}
		return "/jsp/newsTemplate/articleUpdate";
	}
	/**
	 * 删除单条图文
	 */
	@RequestMapping("delNewsitem")
	@ResponseBody
	public void delNewsitem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		JsonModel jsonModel = msgService.delWxNewsitem(id);
		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 修改单条图文
	 */
	@RequestMapping("updNewsitem")
	@ResponseBody
	public void updNewsitem(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JsonModel jsonModel = null;
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String content = request.getParameter("content");
		String url = request.getParameter("url");
		String sort = request.getParameter("sort");

		String accountId = "";

		InputStream input = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile = multipartRequest.getFile("coverimg");
		String filename = mfile.getOriginalFilename();
		if ("".equals(filename)) {
			jsonModel = msgService.updWxNewsitem(id, title, author, description, content, url, sort, accountId);
			
		}else {
			try {
				input = mfile.getInputStream();
			} catch (Exception e) {
				// TODO: handle exception
			}
			jsonModel = msgService.updWxNewsitem(id, title, author, description, content, input, filename, url, sort, accountId);
		}
		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 删除图文消息
	 */
	@RequestMapping("delNewstemplate")
	@ResponseBody
	public void delNewstemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String tempId = request.getParameter("id");

		JsonModel jsonModel = msgService.delNewsTemplate(tempId);

		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 自动回复消息列表
	 */
	@RequestMapping("autoresponseDatagrid")
	@ResponseBody
	public void autoresponseDatagrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<WxAutoresponse> list = msgService.getWxAutoresponseList();
		
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);

		JsonUtil.objectToJson(dataGrid, response);
	}
	/**
	 * 根据消息类别(文本/图文)获取消息模板列表
	 */
	@RequestMapping("getTemplateByMsgtype")
	@ResponseBody
	public void getTemplateByMsgtype(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String msgType = request.getParameter("msgtype");

		List<ComboboxModel> cList = msgService.getTemplateByMsgType(msgType);

		JsonUtil.objectToJson(cList, response);
	}
	/**
	 * 添加自动回复消息
	 */
	@RequestMapping("/addAutoresponse")
	@ResponseBody
	public void addAutoresponse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("keyword");
		String msgType = request.getParameter("msgType");
		String template = request.getParameter("template");

		JsonModel jsonModel = msgService.addWxAutoresponse(keyword, msgType,
				template, "1");

		JsonUtil.objectToJson(jsonModel, response);

	}
	/**
	 * 删除自动回复消息
	 */
	@RequestMapping("delAutoresponse")
	@ResponseBody
	public void delAutoresponse(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String ids = request.getParameter("ids");
		JsonModel jsonModel = msgService.delWxAutoresponse(ids);
		JsonUtil.objectToJson(jsonModel, response);
	}

	/**
	 * 群发消息列表
	 */
	@RequestMapping("massDatagrid")
	@ResponseBody
	public void massDatagrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<WxMass> list = msgService.getWxMassList();
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);
		JsonUtil.objectToJson(dataGrid, response);
	}
	/**
	 * 添加群发消息
	 */
	@RequestMapping("addmass")
	@ResponseBody
	public void addmass(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String massName = request.getParameter("massName");

		JsonModel jsonModel = msgService.addWxMass(massName, "1");
		JsonUtil.objectToJson(jsonModel, response);
	}
	/**
	 * 删除群发消息
	 */
	@RequestMapping("delmass")
	@ResponseBody
	public void delmass(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String ids = request.getParameter("ids");

		JsonModel jsonModel = msgService.delWxMassByIds(ids);

		JsonUtil.objectToJson(jsonModel, response);
	}
	
	
	/**
	 * 一条群发消息的内容列表
	 */
	@RequestMapping("articleDatagrid")
	@ResponseBody
	public void articleDatagrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String massId = request.getParameter("massId");
		List<ArticleModel> list = msgService.getWxArticleListByMassId(massId);
		if (list != null) {
			
			DataGrid dataGrid = new DataGrid();
			dataGrid.setTotal(list.size());
			dataGrid.setRows(list);
			
			JsonUtil.objectToJson(dataGrid, response);
		}
	}
	/**
	 * 添加群发消息——图文
	 */
	@RequestMapping("addMassNews")
	@ResponseBody
	public void addMassNews(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		
		String showcover = request.getParameter("showcover");
		showcover = ("true".equals(showcover))? "1" : "0";
		
		String digest = request.getParameter("digest");
		String content = request.getParameter("content");
		String url = request.getParameter("url");
		String sortString = request.getParameter("sort");
		Integer sort = 0;
		if (sortString != null && !"".equals(sortString)) {
			sort = Integer.valueOf(sortString);
		}
		String massId = request.getParameter("massId");

		String accountId = "";

		InputStream input = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile = multipartRequest.getFile("coverimg");
		String filename = mfile.getOriginalFilename();
		try {
			input = mfile.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JsonModel jsonModel = msgService.addWxArticle(title, author, showcover,
				digest, content, input, filename, url, sort, massId,
				accountId);
		
		JsonUtil.objectToJson(jsonModel, response);

	}
	
	/**
	 * 跳转到群发消息预览页面
	 */
	@RequestMapping("massShow")
	public String toMassShow(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String massId = request.getParameter("id");

		List<ArticleModel> list = msgService.getWxArticleListByMassId(massId);
		
		request.setAttribute("list", list);
		return "/jsp/mass/massShow";
	}
	/**
	 * 获取分组列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("groupDatagrid")
	@ResponseBody
	public void groupDatagrid(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		List<WxGroup> list = accountService.getGroupListByAccountId("1");
		
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(list.size());
		dataGrid.setRows(list);

		JsonUtil.objectToJson(dataGrid, response);
	}
	
	/**
	 * 跳转到智能回复
	 */
	@RequestMapping("toSmart")
	public String toSmart(HttpServletRequest request, HttpServletResponse response){
		String accountId = (String) request.getSession().getAttribute("accountId");
		WxSmart smart = msgService.getWxSmartByAccountId(accountId);
		if (smart != null) {
			request.setAttribute("subscribe", ("yes".equals(smart.getSubscribe())) ? "checked" : "");
			if ("".equals(smart.getSubscribeKey())) {
				request.setAttribute("subscribekeyid","");
				request.setAttribute("subscribekey","");
			}else {
				request.setAttribute("subscribekeyid",smart.getSubscribeKey());
				request.setAttribute("subscribekey",msgService.getKeywordById(smart.getSubscribeKey()));
			}
			
			request.setAttribute("nomatch", ("yes".equals(smart.getNomatch())) ? "checked" : "");
			if ("".equals(smart.getNomatchKey())) {
				request.setAttribute("nomatchkeyid", "");
				request.setAttribute("nomatchkey", "");
			}else {
				request.setAttribute("nomatchkeyid", smart.getNomatchKey());
				request.setAttribute("nomatchkey", msgService.getKeywordById(smart.getNomatchKey()));
			}
		}else {
			request.setAttribute("subscribe", "");
			request.setAttribute("subscribekey", "");
			request.setAttribute("subscribekeyid", "");
			request.setAttribute("nomatch", "");
			request.setAttribute("nomatchkey", "");
			request.setAttribute("nomatchkeyid", "");
		}
		return "jsp/message/smart";
	}
	
	/**
	 * 修改智能回复设置
	 */
	@RequestMapping("updsmart")
	@ResponseBody
	public void updsmart(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String accountId = (String) request.getSession().getAttribute("accountId");
		String subkey = request.getParameter("key1");
		String nomatchkey = request.getParameter("key2");
		
		String sub = ("".equals(subkey)) ? "no" : "yes";
		String nomatch = ("".equals(nomatchkey)) ? "no" : "yes";
		JsonModel jsonModel = msgService.updateSmart(accountId, sub, subkey, nomatch, nomatchkey);
		JsonUtil.objectToJson(jsonModel, response);
	}
	
	
	
	
	
	
	
	
	
	
	
}

package com.weixin.web.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JMenu;

import myUtil.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.util.MessageUtil;

import com.weixin.core.constant.Globals;
import com.weixin.core.util.BaiduUpload;
import com.weixin.web.dao.ApplyarticlesversionDao;
import com.weixin.web.dao.ArticlesapplyDao;
import com.weixin.web.dao.CurrentstatusDao;
import com.weixin.web.dao.MessageDao;
import com.weixin.web.dao.UserDao;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.dao.WxArticleDao;
import com.weixin.web.dao.WxAutoresponseDao;
import com.weixin.web.dao.WxMassDao;
import com.weixin.web.dao.WxNewsitemDao;
import com.weixin.web.dao.WxNewstemplateDao;
import com.weixin.web.dao.WxSmartDao;
import com.weixin.web.dao.WxTexttemplateDao;
import com.weixin.web.entity.Articlesapply;
import com.weixin.web.entity.Currentstatus;
import com.weixin.web.entity.Message;
import com.weixin.web.entity.Tuser;
import com.weixin.web.entity.message.WxArticle;
import com.weixin.web.entity.message.WxAutoresponse;
import com.weixin.web.entity.message.WxMass;
import com.weixin.web.entity.message.WxNewsitem;
import com.weixin.web.entity.message.WxNewstemplate;
import com.weixin.web.entity.message.WxSmart;
import com.weixin.web.entity.message.WxTexttemplate;
import com.weixin.web.model.ApplyMsg;
import com.weixin.web.model.ArticleModel;
import com.weixin.web.model.ComboboxModel;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.MsgService;

@Service
@Transactional
public class MsgServiceImpl implements MsgService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ArticlesapplyDao articlesapplyDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private CurrentstatusDao currentstatusDao;
	@Autowired
	private ApplyarticlesversionDao applyarticlesversionDao;
	@Autowired
	private WXmenuDao wXmenuDao;
	@Autowired
	private WxTexttemplateDao wxTexttemplateDao;
	@Autowired
	private WxNewstemplateDao wxNewstemplateDao;
	@Autowired
	private WxNewsitemDao wxNewsitemDao;
	@Autowired
	private WxSmartDao wxSmartDao;
	@Autowired
	private WxAutoresponseDao wxAutoresponseDao;
	@Autowired
	private WxMassDao wxMassDao;
	@Autowired
	private WxArticleDao wxArticleDao;
	
	

	public List<ApplyMsg> getMyMsgByUser(String userId, int status) {
		List<ApplyMsg> amList = new ArrayList<ApplyMsg>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("owner", userId);

		List<Articlesapply> list = articlesapplyDao
				.find(
						"from Articlesapply a where a.currentstatus.id = :status and a.owner = :owner",
						params);

		if (list.size() > 0) {
			for (Articlesapply aa : list) {
				ApplyMsg am = new ApplyMsg();
				am.setId(aa.getId());
				am.setMsg(aa.getMessage().getContent());
				am.setCreatePerson(aa.getApplyUser());
				am.setCreateTime(aa.getApplyTime());
				am.setStatus(aa.getCurrentstatus().getCurrentStatus());

				amList.add(am);
			}

			return amList;
		} else {
			return null;
		}
	}

	public JsonModel add(String title, String msgType, String content, String userId) {
		JsonModel json = new JsonModel();
		try {
			Message message = new Message();
			message.setId(UUID.randomUUID().toString());
			message.setTitle(title);
			message.setMsgType(msgType);
			message.setContent(content);
			message.setCreateTime(DateUtil.timeFormat());

			messageDao.save(message);

			Articlesapply articlesapply = new Articlesapply();
			articlesapply.setId(UUID.randomUUID().toString());
			articlesapply.setMessage(message);
			articlesapply.setApplyUser(userId);
			articlesapply.setApplyReason(null);
			articlesapply.setCurrentstatus(currentstatusDao.get(
					Currentstatus.class, 0));// 因为定义为int类型，所以不能用string
			articlesapply.setApplyTime(DateUtil.timeFormat());
			articlesapply.setOwner(userId);

			articlesapplyDao.save(articlesapply);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("添加失败");
		}
		return json;
	}

	public JsonModel submitTask(String id, String userId) {
		JsonModel json = new JsonModel();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("owner", userId);

		Articlesapply aa = articlesapplyDao.get(
				"from Articlesapply a where a.id = :id and a.owner = :owner ",
				params);
		if (aa != null) {
			aa.setOwner(userDao.get(Tuser.class, userId).getTuser().getId());

			articlesapplyDao.update(aa);
			json.setSuccess(true);
			json.setMsg("提交成功");
		} else {
			json.setSuccess(false);
			json.setMsg("提交失败");
		}
		return json;
	}
	/**
	 * 驳回申请
	 */
	public JsonModel refuse(String id, String userId) {
		JsonModel json = new JsonModel();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("owner", userId);
		
		Articlesapply aa = articlesapplyDao.get(
				"from Articlesapply a where a.id = :id and a.owner = :owner ",
				params);
		if (aa != null) {
			aa.setOwner(aa.getApplyUser());
			aa.setCurrentstatus(currentstatusDao.get(Currentstatus.class, 3));

			articlesapplyDao.update(aa);
			json.setSuccess(true);
			json.setMsg("驳回成功");
		}else {
			json.setSuccess(false);
			json.setMsg("驳回失败");
		}
		return json;
	}

	public List<WxTexttemplate> getAllTextTemplate() {
		return wxTexttemplateDao.find("from WxTexttemplate");
	}

	@Override
	public JsonModel delTextTemplate(String tempId) {
		JsonModel jModel = new JsonModel();
		WxTexttemplate wt = wxTexttemplateDao.get(WxTexttemplate.class, tempId);
		if (wt != null) {
			wxTexttemplateDao.delete(wt);
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
		} else {
			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}

	@Override
	public JsonModel addTextTemplate(String name, String content) {
		JsonModel jModel = new JsonModel();

		WxTexttemplate wxt = new WxTexttemplate();
		wxt.setId(UUID.randomUUID().toString());
		wxt.setAddTime(DateUtil.timeFormat());
		wxt.setAccountId("123");
		wxt.setTemplateName(name);
		wxt.setContent(content);

		wxTexttemplateDao.save(wxt);
		jModel.setSuccess(true);
		jModel.setMsg("新增模板成功");
		return jModel;
	}

	@Override
	public JsonModel addNewsTemplate(String name) {
		// TODO Auto-generated method stub
		WxNewstemplate wxNewstemplate = new WxNewstemplate();
		wxNewstemplate.setId(UUID.randomUUID().toString());
		wxNewstemplate.setTemplateName(name);
		wxNewstemplate.setAccountId("");
		wxNewstemplate.setAddTime(DateUtil.timeFormat());
		
		wxNewstemplateDao.save(wxNewstemplate);
		return null;
	}

	@Override
	public JsonModel delNewsTemplate(String tempId) {
		JsonModel jModel = new JsonModel();
		WxNewstemplate wt = wxNewstemplateDao.get(WxNewstemplate.class, tempId);
		if (wt != null) {
			wxNewstemplateDao.delete(wt);
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
		} else {
			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}

	@Override
	public List<WxNewstemplate> getAllNewsTemplate() {

		return wxNewstemplateDao.find("from WxNewstemplate");
	}

	@Override
	public List<WxNewsitem> getWxNewsitemsBytempId(String tempId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tempid", tempId);

		return wxNewsitemDao.find(
				"from WxNewsitem w where w.templateId = :tempid", params);
	}

	@Override
	public List<WxAutoresponse> getWxAutoresponseList() {
		List<WxAutoresponse> list = new ArrayList<WxAutoresponse>();
		
		List<WxAutoresponse> list2 = wxAutoresponseDao.find("from WxAutoresponse ");
		if (list2.size() > 0) {
			for (WxAutoresponse wxAutoresponse : list2) {
				WxAutoresponse autoresponse = new WxAutoresponse();
				BeanUtils.copyProperties(wxAutoresponse, autoresponse);
				if (wxAutoresponse.getMsgType().equals(Globals.AUTORESP_TEXT)) {
					autoresponse.setMsgType("文本消息");
				}else {
					autoresponse.setMsgType("图文消息");
				}
				list.add(autoresponse);
			}
		}
		return list;
	}

	@Override
	public List<ComboboxModel> getTemplateByMsgType(String msgType) {
		List<ComboboxModel> combobox = new ArrayList<ComboboxModel>();
		if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
			List<WxTexttemplate> list = wxTexttemplateDao
					.find("from WxTexttemplate");
			if (list.size() > 0) {
				for (WxTexttemplate wxt : list) {
					ComboboxModel comboboxModel = new ComboboxModel();
					comboboxModel.setCvalue(wxt.getId());
					comboboxModel.setCtext(wxt.getTemplateName());

					combobox.add(comboboxModel);
				}
				return combobox;
			}
		} else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)) {
			List<WxNewstemplate> list = wxNewstemplateDao
					.find("from WxNewstemplate ");
			if (list.size() > 0) {
				for (WxNewstemplate wxn : list) {
					ComboboxModel comboboxModel = new ComboboxModel();
					comboboxModel.setCvalue(wxn.getId());
					comboboxModel.setCtext(wxn.getTemplateName());

					combobox.add(comboboxModel);
				}
				return combobox;
			}

		}
		return null;
	}

	@Override
	public JsonModel addWxAutoresponse(String keyword, String msgType,
			String template, String accountId) {
		JsonModel jModel = new JsonModel();
		
		WxAutoresponse wxAutoresponse = new WxAutoresponse();
		wxAutoresponse.setId(UUID.randomUUID().toString());
		wxAutoresponse.setAccountId(accountId);
		wxAutoresponse.setAddTime(DateUtil.timeFormat());
		wxAutoresponse.setKeyword(keyword);
		wxAutoresponse.setMsgType(msgType);
		wxAutoresponse.setResTemplateId(template);
		try {
			if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)) {
				wxAutoresponse.setTemplateName(wxTexttemplateDao.get(
						WxTexttemplate.class, template).getTemplateName());
			}
			else if (msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_NEWS)) {
				wxAutoresponse.setTemplateName(wxNewstemplateDao.get(WxNewstemplate.class, template).getTemplateName());
			}
			wxAutoresponseDao.save(wxAutoresponse);
			
			jModel.setSuccess(true);
			jModel.setMsg("新增成功");
		} catch (Exception e) {
			// TODO: handle exception
			jModel.setSuccess(false);
			jModel.setMsg("新增失败");
		}
		return jModel;
	}

	@Override
	public JsonModel addNewsitem(String title, String author, String description,
			String content, InputStream is, String imgName, String url, String sort, 
			String templateId, String accountId) {
		JsonModel jModel = new JsonModel();
		WxNewsitem wxNewsitem = new WxNewsitem();
		try {
			wxNewsitem.setId(UUID.randomUUID().toString());
			wxNewsitem.setTitle(title);
			wxNewsitem.setAuthor(author);
			wxNewsitem.setDescription(description);
			wxNewsitem.setContent(content);
			
			String picUrl = BaiduUpload.baiduUploadImage(is, "news", imgName, true);
			wxNewsitem.setPicUrl(picUrl);
			wxNewsitem.setUrl(url);
			wxNewsitem.setSort(sort);
			wxNewsitem.setTemplateId(templateId);
			wxNewsitem.setAccountId(accountId);
			wxNewsitem.setAddTime(DateUtil.timeFormat());
			
			wxNewsitemDao.save(wxNewsitem);
			
			jModel.setSuccess(true);
			jModel.setMsg("添加成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("添加失败");
		}
		return jModel;
	}

	@Override
	public WxNewsitem getWxNewsitemById(String id){
		return wxNewsitemDao.get(WxNewsitem.class, id);
	}
	
	@Override
	public JsonModel delWxNewsitem(String id){
		JsonModel jModel = new JsonModel();
		WxNewsitem wxNewsitem = wxNewsitemDao.get(WxNewsitem.class, id);
		if (wxNewsitem != null) {
			wxNewsitemDao.delete(wxNewsitem);
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}
	@Override
	public JsonModel delWxAutoresponse(String ids) {
		JsonModel jmModel = new JsonModel();
		// TODO Auto-generated method stub
		String[] idss = ids.split(",");
		for (int i = 0; i < idss.length; i++) {
			WxAutoresponse wxa = wxAutoresponseDao.get(WxAutoresponse.class, idss[i]);
			if (wxa != null) {
				wxAutoresponseDao.delete(wxa);
			}
		}
		jmModel.setSuccess(true);
		jmModel.setMsg("删除成功!");
		return jmModel;
	}

	@Override
	public JsonModel updWxNewsitem(String id, String title, String author,
			String description, String content, InputStream is, String imgName,
			String url, String sort, String accountId) {
		JsonModel jModel = new JsonModel();
		
		WxNewsitem wxNewsitem = wxNewsitemDao.get(WxNewsitem.class, id);
		if (wxNewsitem != null) {
			try {
				wxNewsitem.setTitle(title);
				wxNewsitem.setAuthor(author);
				wxNewsitem.setDescription(description);
				wxNewsitem.setContent(content);
				
				String picUrl = BaiduUpload.baiduUploadImage(is, "news", imgName, true);
				wxNewsitem.setPicUrl(picUrl);
				wxNewsitem.setUrl(url);
				wxNewsitem.setSort(sort);
				
				wxNewsitemDao.update(wxNewsitem);
				
				jModel.setSuccess(true);
				jModel.setMsg("修改成功");
			} catch (Exception e) {
				jModel.setSuccess(false);
				jModel.setMsg("修改失败");
			}
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("修改失败");
		}
		return jModel;
	}

	@Override
	public JsonModel updWxNewsitem(String id, String title, String author,
			String description, String content, String url, String sort,
			String accountId) {
		JsonModel jModel = new JsonModel();
		WxNewsitem wxNewsitem = wxNewsitemDao.get(WxNewsitem.class, id);
		if (wxNewsitem != null) {
			try {
				wxNewsitem.setTitle(title);
				wxNewsitem.setAuthor(author);
				wxNewsitem.setDescription(description);
				wxNewsitem.setContent(content);
				wxNewsitem.setUrl(url);
				wxNewsitem.setSort(sort);
				
				wxNewsitemDao.update(wxNewsitem);
				
				jModel.setSuccess(true);
				jModel.setMsg("修改成功");
			} catch (Exception e) {
				jModel.setSuccess(false);
				jModel.setMsg("修改失败");
			}
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("修改失败");
		}
		return jModel;
	}
	
	@Override
	public List<WxMass> getWxMassList() {
		// TODO Auto-generated method stub
		return wxMassDao.find("from WxMass");
	}
	
	public JsonModel addWxMass(String massName, String accountId){
		JsonModel jModel = new JsonModel();
		WxMass wxMass = new WxMass();
		try {
			wxMass.setId(UUID.randomUUID().toString());
			wxMass.setAccountId(accountId);
			wxMass.setMassName(massName);
			wxMass.setAddTime(DateUtil.timeFormat());
			wxMass.setSend("no");
			
			wxMassDao.save(wxMass);
			jModel.setSuccess(true);
			jModel.setMsg("新增成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("新增失败");
		}
		return jModel;
	}

	@Override
	public JsonModel delWxMassByIds(String ids) {
		JsonModel jModel = new JsonModel();
		if (ids != null && !StringUtils.isEmpty(ids)) {
			String[] wmids = ids.split(",");
			for (int i = 0; i < wmids.length; i++) {
				WxMass wxMass = wxMassDao.get(WxMass.class, wmids[i]);
				if (wxMass != null) {
					wxMassDao.delete(wxMass);
				}
			}
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
 		}else {
 			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}

	@Override
	public List<ArticleModel> getWxArticleListByMassId(String massId) {
		// TODO Auto-generated method stub
		List<ArticleModel> result = new ArrayList<ArticleModel>();
		List<WxArticle> list = wxArticleDao.find("from WxArticle w where w.wxMass.id = '" + massId + "'");
		if (list.size() > 0 ) {
			for (WxArticle wxArticle : list) {
				ArticleModel articlemodel = new ArticleModel();
				BeanUtils.copyProperties(wxArticle, articlemodel);
				result.add(articlemodel);
			}
			return result;
		}
		return null;
	}

	@Override
	public JsonModel addWxArticle(String title, String author, String showcover, String digest,
			String content, InputStream is, String imgName, String url,
			Integer sort, String massId, String accountId) {
		JsonModel jModel = new JsonModel();
		
		WxArticle wxArticle = new WxArticle();
		try {
			wxArticle.setId(UUID.randomUUID().toString());
			wxArticle.setTitle(title);
			wxArticle.setAuthor(author);
			wxArticle.setDigest(digest);
			wxArticle.setContent(content);
			
			String picUrl = BaiduUpload.baiduUploadImage(is, "news", imgName, true);
			
			wxArticle.setThumbMediaUrl(picUrl);
			wxArticle.setContentSourceUrl(url);
			wxArticle.setSort(sort);
			WxMass mass = wxMassDao.get(WxMass.class, massId);
			if (mass != null) {
				wxArticle.setWxMass(mass);
			}
			wxArticle.setAddTime(DateUtil.timeFormat());
			wxArticle.setShowCoverPic(showcover);//是否显示封面，1为显示，0为不显示
			
			wxArticleDao.save(wxArticle);
			
			jModel.setSuccess(true);
			jModel.setMsg("添加成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("添加失败");
		}
		return jModel;
	}
	
	public String getKeywordById(String id){
		WxAutoresponse autoresponse = wxAutoresponseDao.get(WxAutoresponse.class, id);
		if (autoresponse != null) {
			return autoresponse.getKeyword();
		}
		return "";
	}
	
	public WxSmart getWxSmartByAccountId(String accountId){
		return wxSmartDao.get("from WxSmart w where w.accountId = '" + accountId + "'");
	}

	public JsonModel updateSmart(String accountId, String sub, String subkey, String nomatch, String nomatchkey){
		JsonModel json = new JsonModel();
		try {
			WxSmart smart = wxSmartDao.get("from WxSmart w where w.accountId = '" + accountId + "'");
			if (smart == null) {
				WxSmart wxSmart = new WxSmart();
				wxSmart.setId(UUID.randomUUID().toString());
				wxSmart.setAccountId(accountId);
				wxSmart.setAddTime(DateUtil.timeFormat());
				wxSmart.setSubscribe(sub);
				wxSmart.setSubscribeKey(subkey);
				wxSmart.setNomatch(nomatch);
				wxSmart.setNomatchKey(nomatchkey);

				wxSmartDao.save(wxSmart);
			}else {
				smart.setSubscribe(sub);
				smart.setSubscribeKey(subkey);
				smart.setNomatch(nomatch);
				smart.setNomatchKey(nomatchkey);
				
				wxSmartDao.update(smart);
			}
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("修改失败");
		}
		return json;
	}
	

}

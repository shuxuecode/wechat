package com.weixin.web.service;

import java.io.InputStream;
import java.util.List;

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
/**
 * 消息service接口
 * @author zsx
 *
 */
public interface MsgService {
	/**
	 * 根据用户id和信息申请的状态获取该用户能看到的信息
	 */
	List<ApplyMsg> getMyMsgByUser(String userId, int status);
	/**
	 * 提交信息到上级
	 */
	JsonModel submitTask(String id, String userId);

	/**
	 * 驳回申请
	 */
	JsonModel refuse(String id, String userId);
	/**
	 * 添加申请信息——需要改为群发消息的id
	 */
	JsonModel add(String title, String msgType, String content, String userId);

	/**
	 * 获取所有的文本消息模板——改为根据账户id获取 byaccountId
	 * 
	 * @return
	 */
	List<WxTexttemplate> getAllTextTemplate();

	/**
	 * 删除文本消息模板
	 * 
	 * @param tempId
	 * @return
	 */
	JsonModel delTextTemplate(String tempId);

	/**
	 * 新增文本消息模板
	 * 
	 * @param name
	 * @param content
	 * @return
	 */
	JsonModel addTextTemplate(String name, String content);

	/**
	 * 获取所有的图文消息模板——改为根据账户id获取 byaccountId
	 * 
	 * @return
	 */
	List<WxNewstemplate> getAllNewsTemplate();

	/**
	 * 删除图文消息模板
	 * 
	 * @param tempId
	 * @return
	 */
	JsonModel delNewsTemplate(String tempId);

	/**
	 * 新增图文消息模板
	 */
	JsonModel addNewsTemplate(String name);
	/**
	 * 根据图文模板id获取该模板下的图文信息
	 */
	List<WxNewsitem> getWxNewsitemsBytempId(String tempId);
	/**
	 * 添加单条图文内容
	 */
	JsonModel addNewsitem(String title, String author, String description,
			String content, InputStream is, String imgName, String url,
			String sort, String templateId, String accountId);
	/**
	 * 根据id获取单条图文内容
	 */
	WxNewsitem getWxNewsitemById(String id);
	/**
	 * 删除单条图文内容
	 */
	JsonModel delWxNewsitem(String id);
	/**
	 * 修改图文消息（有图片更新时）
	 */
	JsonModel updWxNewsitem(String id, String title, String author, String description,
			String content, InputStream is, String imgName, String url,
			String sort, String accountId);
	/**
	 * 修改图文消息（无图片更新时）
	 */
	JsonModel updWxNewsitem(String id, String title, String author, String description,
			String content, String url,String sort, String accountId);
	/**
	 * 获取所有的关键词管理信息列表——改为根据账户id获取 byaccountId
	 */
	List<WxAutoresponse> getWxAutoresponseList();
	/**
	 * 根据消息类别获取该类别下的文本消息列表或图文消息列表
	 */
	List<ComboboxModel> getTemplateByMsgType(String msgType);
	/**
	 * 添加关键词自动回复
	 */
	JsonModel addWxAutoresponse(String keyword, String msgType,
			String template, String accountId);
	/**
	 * 删除关键词
	 */
	JsonModel delWxAutoresponse(String ids);
	
	/**
	 * 获取群发消息列表——改为根据账户id获取 byaccountId
	 * @return
	 */
	List<WxMass> getWxMassList();
	/**
	 * 新增群发消息
	 * @return
	 */
	JsonModel addWxMass(String massName, String accountId);
	/**
	 * 删除群发消息
	 * @param ids
	 * @return
	 */
	JsonModel delWxMassByIds(String ids);
	/**
	 * 根据群发消息Id获取图文消息列表
	 * @param massId
	 * @return
	 */
	List<ArticleModel> getWxArticleListByMassId(String massId);
	/**
	 * 添加群发消息中的单条图文内容
	 */
	JsonModel addWxArticle(String title, String author, String showcover, String digest,
			String content, InputStream is, String imgName, String url,
			Integer sort, String massId, String accountId);
	/**
	 * 根据账户id获取智能回复设置中的信息
	 */
	WxSmart getWxSmartByAccountId(String accountId);
	/**
	 * 根据关键词id获取关键词内容
	 */
	String getKeywordById(String id);
	/**
	 * 修改智能回复设置
	 */
	JsonModel updateSmart(String accountId, String sub, String subkey, String nomatch, String nomatchkey);
	
}

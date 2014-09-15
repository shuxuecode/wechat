package org.weixin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.dao.WxEventDao;
import org.weixin.dao.WxGroupDao;
import org.weixin.dao.WxMessageDao;
import org.weixin.dao.WxUserinfoDao;
import org.weixin.entity.WxEvent;
import org.weixin.entity.WxMessage;
import org.weixin.entity.WxUserinfo;
import org.weixin.entity.message.resp.Article;
import org.weixin.entity.message.resp.NewsMessage;
import org.weixin.entity.message.resp.TextMessage;
import org.weixin.util.MessageUtil;
import org.weixin.util.WeixinUtil;

import com.alibaba.fastjson.JSONObject;
import com.weixin.web.dao.UserDao;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.dao.WxAutoresponseDao;
import com.weixin.web.dao.WxNewsitemDao;
import com.weixin.web.dao.WxNewstemplateDao;
import com.weixin.web.dao.WxTexttemplateDao;
import com.weixin.web.entity.WxAccount;
import com.weixin.web.entity.message.WxAutoresponse;
import com.weixin.web.entity.message.WxNewsitem;
import com.weixin.web.entity.message.WxNewstemplate;
import com.weixin.web.entity.message.WxTexttemplate;
import com.weixin.web.service.AccountService;

@Service
@Transactional
public class WechatService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WxMessageDao wxMessageDao;
	@Autowired
	private WxEventDao wxEventDao;
	@Autowired
	private WxAutoresponseDao wxAutoresponseDao;
	@Autowired
	private WxTexttemplateDao wxTexttemplateDao;
	@Autowired
	private WxNewstemplateDao wxNewstemplateDao;
	@Autowired
	private WxNewsitemDao wxNewsitemDao;
	@Autowired
	private WxGroupDao wxGroupDao;
	@Autowired
	private WxUserinfoDao wxUserinfoDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private WxAccountDao wxAccountDao;
	

	public String coreService(HttpServletRequest request, WxAccount account) {

		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 
			String msgId = requestMap.get("MsgId");
			
			String createTime = requestMap.get("CreateTime");

			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);
			
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
//				respContent = "您发送的是文本消息！\n 10086";
				String content = requestMap.get("Content");
				// 保存接收到的信息
				WxMessage wxMessage = new WxMessage();
				wxMessage.setId(UUID.randomUUID().toString());
				wxMessage.setAccountId(toUserName);
				wxMessage.setToUserName(toUserName);
				wxMessage.setFromUserName(fromUserName);
				wxMessage.setMsgType(msgType);
				wxMessage.setMsgId(msgId);
				wxMessage.setAccountId(account.getId());

				wxMessage.setContent(content);

				wxMessageDao.save(wxMessage);
				// 判断关键字信息中是否管理该文本内容。有的话优先采用数据库中的回复
				WxAutoresponse wxAutoresponse = findKey(content, toUserName);
				if (wxAutoresponse != null) {
					String resMsgType = wxAutoresponse.getMsgType();
					if ("text".equals(resMsgType)) {
						WxTexttemplate wxTexttemplate = wxTexttemplateDao.get(WxTexttemplate.class, wxAutoresponse.getResTemplateId());
						textMessage.setContent(wxTexttemplate.getContent());
						respMessage = MessageUtil.textMessageToXml(textMessage);
						
						return respMessage;
						
					}else if ("news".equals(resMsgType)) {
						List<Article> articleList = new ArrayList<Article>();  
						
						WxNewstemplate wxNewstemplate = wxNewstemplateDao.get(WxNewstemplate.class, wxAutoresponse.getResTemplateId());
						
						List<WxNewsitem> wxNewsitems = wxNewsitemDao.find("from WxNewsitem w where w.templateId = '" + wxNewstemplate.getId() + "'");
						
						if (wxNewsitems.size() > 0) {
							for (WxNewsitem wxn : wxNewsitems) {
								Article article = new Article();
								article.setTitle(wxn.getTitle());
								article.setPicUrl(wxn.getPicUrl());
								article.setDescription(wxn.getDescription());
								article.setUrl(wxn.getUrl());
								
								articleList.add(article);
							}
						}
						
						NewsMessage newsMessage = new NewsMessage(); 
						newsMessage.setFromUserName(toUserName);
						newsMessage.setToUserName(fromUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						// 设置图文消息个数
						newsMessage.setArticleCount(articleList.size());
						// 设置图文消息包含的图文集合 
						newsMessage.setArticles(articleList);
						// 将图文消息对象转换成xml字符串  
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						
						return respMessage;
					}
				}

			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				String picUrl = requestMap.get("PicUrl");
				String mediaId = requestMap.get("MediaId");
				// 保存接收到的信息
				WxMessage wxMessage = new WxMessage();
				wxMessage.setId(UUID.randomUUID().toString());
				wxMessage.setAccountId(toUserName);
				wxMessage.setToUserName(toUserName);
				wxMessage.setFromUserName(fromUserName);
				wxMessage.setMsgType(msgType);
				wxMessage.setMsgId(msgId);
				wxMessage.setAccountId(account.getId());

				wxMessage.setPicUrl(picUrl);
				wxMessage.setMediaId(mediaId);

				wxMessageDao.save(wxMessage);
				

				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;

			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是音频消息！";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				//事件KEY值
				String eventKey = requestMap.get("EventKey");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
					
					WxEvent wxEvent = new WxEvent();
					wxEvent.setId(UUID.randomUUID().toString());
					wxEvent.setFromUserName(fromUserName);
					wxEvent.setToUserName(toUserName);
					wxEvent.setCreateTime(createTime);
					wxEvent.setMsgType(msgType);
					wxEvent.setEvent(eventType);
					//二维码扫描事件
					wxEvent.setEventKey(eventKey);
					String ticket = requestMap.get("Ticket");
					wxEvent.setTicket(ticket);
					wxEvent.setAccountId(account.getId());
					
					wxEventDao.save(wxEvent);
					
					//添加关注者信息
					addUserinfo(toUserName, fromUserName, ticket);
					
					

					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					
					WxEvent wxEvent = new WxEvent();
					wxEvent.setId(UUID.randomUUID().toString());
					wxEvent.setFromUserName(fromUserName);
					wxEvent.setToUserName(toUserName);
					wxEvent.setCreateTime(createTime);
					wxEvent.setMsgType(msgType);
					wxEvent.setEvent(eventType);
					wxEvent.setAccountId(account.getId());
					
					wxEventDao.save(wxEvent);
				}
				
				// 扫描带参数二维码事件 —— 用户已关注时的事件推送
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					respContent = "扫描二维码——您已关注！";
					
					WxEvent wxEvent = new WxEvent();
					wxEvent.setId(UUID.randomUUID().toString());
					wxEvent.setFromUserName(fromUserName);
					wxEvent.setToUserName(toUserName);
					wxEvent.setCreateTime(createTime);
					wxEvent.setMsgType(msgType);
					wxEvent.setEvent(eventType);
					
					wxEvent.setEventKey(eventKey);
					String ticket = requestMap.get("Ticket");
					wxEvent.setTicket(ticket);
					wxEvent.setAccountId(account.getId());
					
					wxEventDao.save(wxEvent);

					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
					
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					//事件KEY值，与自定义菜单接口中KEY值对应
					String key = requestMap.get("EventKey");
				}
				
				// 自定义菜单点击跳转链接事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					//事件KEY值，设置的跳转URL
					String key = requestMap.get("EventKey");
				}
			}

//			textMessage.setContent(respContent);
//			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}

	/**
	 * 根据msgid对接收普通消息排重
	 * 
	 * @param msgId
	 * @return 存在返回true ， 不存在返回 false
	 */
	public boolean findMessageByMsgId(String msgId) {
		WxMessage wxm = wxMessageDao.get("from WxMessage w where w.msgId = '"
				+ msgId + "'");
		return (wxm != null);
	}

	/**
	 * 根据FromUserName + CreateTime 对接收事件推送排重
	 * 
	 * @param msgId
	 * @return 存在返回true ， 不存在返回 false
	 */
	public boolean findEventByFromUserNameAndCreateTime(String fromUserName,
			String createTime) {
		
		WxEvent wxe = wxEventDao.get("from WxEvent w where w.fromUserName = '" + 
				fromUserName + "' and w.createTime = '" + createTime + "'");
		
		return (wxe != null);
	}
	
	/**
	 * 遍历关键字管理中是否存在用户输入的关键字信息
	 * 
	 * @param content
	 * @return
	 */
	private WxAutoresponse findKey(String content, String toUserName) {
		//根据tousername（即微信原始id）获取用户id
		String accountId = accountService.getAccountIdByWxId(toUserName);
		// 获取关键字管理的列表，匹配后返回信息
//		暂时没做用户信息匹配
		List<WxAutoresponse> list = wxAutoresponseDao.find("from WxAutoresponse w where w.accountId = '" + accountId + "'");
		
		for (WxAutoresponse r : list) {
			// 如果包含关键字
			String kw = r.getKeyword();
			String[] allkw = kw.split(",");
			for (String k : allkw) {
				if (k.equals(content)) {
					return r;
				}
			}
		}
		return null;
	}

	/**
	 * 新增关注者信息
	 */
	private void addUserinfo(String toUserName, String openid, String ticket){
		
		String accountId = accountService.getAccountIdByWxId(toUserName);
		if (accountId != null) {
			String accessToken = accountService.getAccessTokenById(accountId);
			JSONObject jsonObject = WeixinUtil.getUserinfo(accessToken, openid);

			WxUserinfo wxuserinfo = new WxUserinfo();
			
			wxuserinfo.setId(UUID.randomUUID().toString());
			wxuserinfo.setSubscribe(jsonObject.getInteger("subscribe").toString());
			wxuserinfo.setOpenid(jsonObject.getString("openid"));
			wxuserinfo.setNickname(jsonObject.getString("nickname"));
			wxuserinfo.setSex(jsonObject.getInteger("sex"));
			wxuserinfo.setLanguage(jsonObject.getString("language"));
			wxuserinfo.setCity(jsonObject.getString("city"));
			wxuserinfo.setProvince(jsonObject.getString("province"));
			wxuserinfo.setCountry(jsonObject.getString("country"));
			wxuserinfo.setHeadimgurl(jsonObject.getString("headimgurl"));
			wxuserinfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
			wxuserinfo.setRemark(jsonObject.getString("remark"));
			wxuserinfo.setAccountid(accountId);
			wxuserinfo.setTag(ticket);
			
			wxUserinfoDao.save(wxuserinfo);
		}
		
		
	}
	
	public WxAccount getWxAccountByWxId(String wxId){
		return wxAccountDao.get("from WxAccount w where w.wxId = =" + wxId + "'");
	} 
}

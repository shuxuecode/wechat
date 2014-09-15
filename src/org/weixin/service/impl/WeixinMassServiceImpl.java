package org.weixin.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.dao.WxGroupDao;
import org.weixin.entity.WxGroup;
import org.weixin.pojo.Articles;
import org.weixin.pojo.Button;
import org.weixin.pojo.CommonButton;
import org.weixin.pojo.ComplexButton;
import org.weixin.pojo.Menu;
import org.weixin.pojo.ViewButton;
import org.weixin.service.WeixinMassService;
import org.weixin.util.MediaUtil;
import org.weixin.util.WeixinUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.core.constant.Globals;
import com.weixin.web.dao.WXmenuDao;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.dao.WxArticleDao;
import com.weixin.web.dao.WxMassDao;
import com.weixin.web.entity.WXmenu;
import com.weixin.web.entity.message.WxArticle;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.AccountService;

@Service
@Transactional
public class WeixinMassServiceImpl implements WeixinMassService {
	
	@Autowired
	private WxGroupDao wxGroupDao;
	@Autowired
	private WxMassDao wxMassDao;
	@Autowired
	private WxArticleDao wxArticleDao;
	@Autowired
	private AccountService accountService;
	
	/**
	 * 群发消息
	 */
	public String mass(String massId , String accountId, String massType, String groupId, List<String> openIdList){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("massid", massId);
		
		List<WxArticle> list = wxArticleDao.find("from WxArticle w where w.wxMass.id = :massid order by w.sort", params);
		
		if (list.size() > 0) {
			List<Articles> articlesList = new ArrayList<Articles>();
			String accessToken = accountService.getAccessTokenById(accountId);
			for (int i = 0; i < list.size(); i++) {
				WxArticle wxArticle = list.get(i);
				Articles articles = new Articles();
				try {
					//
					String media_id = MediaUtil.getMedia_idByURL(wxArticle.getThumbMediaUrl(), accessToken);
					articles.setThumb_media_id(media_id);
					
				} catch (Exception e) {
					return "获取图片缩略图出错，请重新发送！";
				}
				articles.setAuthor(wxArticle.getAuthor());
				articles.setTitle(wxArticle.getTitle());
				articles.setContent_source_url(wxArticle.getContentSourceUrl());
				articles.setContent(wxArticle.getContent());
				articles.setDigest(wxArticle.getDigest());
				articles.setShow_cover_pic(wxArticle.getShowCoverPic());
				
				articlesList.add(articles);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("articles", articlesList);
			
			System.out.println(JSON.toJSONString(map));
			
			JSONObject jsonObject = WeixinUtil.uploadNews(accessToken, JSON.toJSONString(map));
			if (null != jsonObject) {  
				//{"created_at":1409197412,"media_id":"KDZiKskeeQ5xlNKzXNEEwvwxB-i0Un5wypK9HQWmEneLBKBGvk5RkWHftOYZZsIN","type":"news"}
				String media_id = jsonObject.getString("media_id");
	            if (null != media_id) {
	            	if (massType.equals(Globals.SEND_BY_GROUP)) {
            			JSONObject sendByGroupResult = WeixinUtil.sendallByGroup(accessToken, groupId, media_id);
	            		System.out.println(sendByGroupResult);
	            		if (0 == sendByGroupResult.getInteger("errcode")) {
							return "群发成功";
						}else {
							return "按组发送时出错，错误代码为：" + sendByGroupResult.getInteger("errcode");
						}
	            	}else if (massType.equals(Globals.SEND_BY_OPENID)) {
						JSONObject sendByOpendIDResult = WeixinUtil.sendByOpenID(accessToken, openIdList, media_id);
						System.out.println(sendByOpendIDResult);
						if (0 == sendByOpendIDResult.getInteger("errcode")) {
							return "群发成功";
						}else {
							return "按OpenId发送时出错，错误代码为：" + sendByOpendIDResult.getInteger("errcode");
						}
	            	}
	            } else {
					return "出错了，错误代码为：" + jsonObject.getInteger("errcode");
				}
	        }
		}
		return "ok";
	}
	
	
	
	/**
	 * 上传图文素材，获取图文消息的mediaid
	 * https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN
	 */
	public void uploadnews(String massId , String accountId){
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("massid", massId);
		
		List<WxArticle> list = wxArticleDao.find("from WxArticle w where w.wxMass.id = :massid order by w.sort", params);
		
		if (list.size() > 0) {
			List<Articles> articlesList = new ArrayList<Articles>();
			String accessToken = accountService.getAccessTokenById(accountId);
			for (int i = 0; i < list.size(); i++) {
				WxArticle wxArticle = list.get(i);
				Articles articles = new Articles();
				//
				String media_id = MediaUtil.getMedia_idByURL(wxArticle.getThumbMediaUrl(), accessToken);
				articles.setThumb_media_id(media_id);
				articles.setAuthor(wxArticle.getAuthor());
				articles.setTitle(wxArticle.getTitle());
				articles.setContent_source_url(wxArticle.getContentSourceUrl());
				articles.setContent(wxArticle.getContent());
				articles.setDigest(wxArticle.getDigest());
				articles.setShow_cover_pic(wxArticle.getShowCoverPic());
				
				articlesList.add(articles);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("articles", articlesList);
			
			System.out.println(JSON.toJSONString(map));
			
			JSONObject jsonObject = WeixinUtil.uploadNews(accessToken, JSON.toJSONString(map));
			if (null != jsonObject) {  
	            if (0 != jsonObject.getInteger("errcode")) { 
	            	String media_id = jsonObject.getString("media_id");
	            	
	            }  
	        }
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

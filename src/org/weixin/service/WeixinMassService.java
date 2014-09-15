package org.weixin.service;

import java.io.IOException;
import java.util.List;

import org.weixin.entity.WxGroup;

import com.weixin.web.model.json.JsonModel;


public interface WeixinMassService {
	/**
	 * 群发消息
	 */
	String mass(String massId , String accountId, String massType, String groupId, List<String> openIdList);
}

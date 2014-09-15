package org.weixin.service;

import java.io.IOException;

import com.weixin.web.model.json.JsonModel;


public interface WeixinMenuService {
	
	JsonModel sync(String accountId) throws IOException;
}

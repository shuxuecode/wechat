package com.weixin.web.service;

import java.util.List;

import com.weixin.web.entity.WxQrcode;
import com.weixin.web.model.Userinfo;
import com.weixin.web.model.json.JsonModel;
/**
 * 二维码service接口
 * @author zsx
 *
 */

public interface QrcodeService {
	/**
	 * 获取所有的二维码信息列表——需要改为根据accountid获取
	 */
	List<WxQrcode> getAllQrcode();
	/**
	 * 删除二维码
	 */
	JsonModel delWxqrcode(String codeId);
	/**
	 * 根据id获取生成二维码的ticket
	 */
	String getTicketById(String id);
	/**
	 * 添加二维码
	 */
	JsonModel addWxQrcode(String params, String accountId, String action);
	/**
	 * 获取所有的关注该微信账号的用户信息列表——需要改为根据账户id—— byaccountid
	 */
	List<Userinfo> getAllWxUserinfo();
	/**
	 * 根据关注用户id获取用户openid
	 */
	String getOpenIdByUserinfoId(String id);
}

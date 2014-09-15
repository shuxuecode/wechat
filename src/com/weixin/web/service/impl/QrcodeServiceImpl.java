package com.weixin.web.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import myUtil.DateUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin.dao.WxUserinfoDao;
import org.weixin.entity.WxUserinfo;
import org.weixin.util.WeixinUtil;

import com.weixin.core.constant.Globals;
import com.weixin.core.util.ImageUtil;
import com.weixin.web.dao.WxAccountDao;
import com.weixin.web.dao.WxQrcodeDao;
import com.weixin.web.entity.WxQrcode;
import com.weixin.web.model.Userinfo;
import com.weixin.web.model.json.JsonModel;
import com.weixin.web.service.AccountService;
import com.weixin.web.service.QrcodeService;

@Service
@Transactional
public class QrcodeServiceImpl implements QrcodeService {
	
	@Autowired
	private WxQrcodeDao wxQrcodeDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private WxUserinfoDao wxUserinfoDao;

	@Override
	public List<WxQrcode> getAllQrcode() {
		return wxQrcodeDao.find("from WxQrcode");
	}

	@Override
	public JsonModel delWxqrcode(String codeId) {
		JsonModel jModel = new JsonModel();
		WxQrcode code = wxQrcodeDao.get(WxQrcode.class, codeId);
		if (code != null) {
			wxQrcodeDao.delete(code);
			jModel.setSuccess(true);
			jModel.setMsg("删除成功");
		}else {
			jModel.setSuccess(false);
			jModel.setMsg("删除失败");
		}
		return jModel;
	}

	@Override
	public String getTicketById(String id) {
		WxQrcode code = wxQrcodeDao.get(WxQrcode.class, id);
		if (code != null) {
			return code.getTicket();
		}else {
			return null;
		}
	}

	@Override
	public JsonModel addWxQrcode(String params, String accountId, String action) {
		JsonModel jModel = new JsonModel();
		try {
			WxQrcode code = new WxQrcode();
			code.setId(UUID.randomUUID().toString());
			code.setAccountId(accountId);
			code.setCreateTime(DateUtil.timeFormat());
			if ("on".equals(action)) {
				code.setActionName(Globals.QR_LIMIT_SCENE);
			}else {
				code.setActionName(Globals.QR_SCENE);
			}
			code.setParams(params);
			
			Integer scenceId = getScenceId();
			if (scenceId != 0 && scenceId < 100000) {
				code.setScenceId(getScenceId() + 1);
			}else {
				throw new Exception("场景值添加失败");
			}
			
			String accessToken = accountService.getAccessTokenById(accountId);
			if (accessToken != null) {
				String ticket = WeixinUtil.createQrcodeTicket(accessToken, code.getScenceId().toString(), true);
				System.out.println(ticket);
				code.setTicket(ticket);
			}
			//提醒：TICKET记得进行UrlEncode
			String ticketurl = WeixinUtil.get_qrcode + code.getTicket();
			System.out.println(ticketurl);
			String url = ImageUtil.uploadNetImage(ticketurl, "qrcode", code.getScenceId().toString() + ".jpg", true);
			code.setUrl(url);
			
			wxQrcodeDao.save(code);
			jModel.setSuccess(true);
			jModel.setMsg("添加成功");
		} catch (Exception e) {
			jModel.setSuccess(false);
			jModel.setMsg("添加失败");
		}
		return jModel;
	}
	
	
	/**
	 * 得到二维码列表中最大的scenceId
	 * @return
	 */
	public int getScenceId(){
		int scenceId = 0;
		List<WxQrcode> list = wxQrcodeDao.find("from WxQrcode w order by w.scenceId desc ", 1, 1);
		if (list.size() != 0) {
			scenceId = list.get(0).getScenceId();
		}
		return scenceId;
	}
	
	
	public List<Userinfo> getAllWxUserinfo(){
		List<Userinfo> result = new ArrayList<Userinfo>();
		
		List<WxUserinfo> list = wxUserinfoDao.find("from WxUserinfo w order by w.subscribeTime desc");
		if (list.size() > 0) {
			for (WxUserinfo wxuserinfo : list) {
				Userinfo userinfo = new Userinfo();
				BeanUtils.copyProperties(wxuserinfo, userinfo);
				//性别
				Integer sex = wxuserinfo.getSex();
				if (sex == Globals.male) {
					userinfo.setSexName("男");
				}else if (sex == Globals.female) {
					userinfo.setSexName("女");
				}else {
					userinfo.setSexName("未知");
				}
				
				//关注时间格式转换
				String subscribeTime = wxuserinfo.getSubscribeTime() + "000";
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				userinfo.setSubscribeTime(sdf.format(new Date(Long.valueOf(subscribeTime))));
				
				//扫描二维码
				String ticket = wxuserinfo.getTag();
				if (!"".equals(ticket)) {
					WxQrcode wxQrcode = wxQrcodeDao.get("from WxQrcode w where w.ticket = '" + ticket + "'");
					if (wxQrcode != null) {
						String params = wxQrcode.getParams();
						userinfo.setOther("扫描了标签为:[ " + params + " ]的二维码");
					}
				}
				
				//分组
				userinfo.setGroup("未分组");
				
				
				result.add(userinfo);
			}
		}
		return result;
		
	}

	@Override
	public String getOpenIdByUserinfoId(String id) {
		WxUserinfo userinfo = wxUserinfoDao.get(WxUserinfo.class, id);
		if (userinfo != null) {
			return userinfo.getOpenid();
		}else {
			return "";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}

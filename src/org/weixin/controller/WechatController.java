package org.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.weixin.service.impl.WechatService;
import org.weixin.util.SignUtil;

import com.weixin.web.entity.WxAccount;
@Controller
@RequestMapping("/wechatController")
public class WechatController {
	
	@Autowired
	private WechatService wechatService;
	
	

	@RequestMapping(value = "/validate/{name}", method = RequestMethod.GET)
	public void wechatGet(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "signature") String signature,
			@RequestParam(value = "timestamp") String timestamp,
			@RequestParam(value = "nonce") String nonce,
			@RequestParam(value = "echostr") String echostr,
			@PathVariable("name") String name ) throws IOException {
		
		PrintWriter out = response.getWriter();
		//根据参数获取该注册用户的信息——token ， 用于验证用户
		WxAccount account = wechatService.getWxAccountByWxId(name);
		if (account != null && account.getToken() != null) {
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
	        if (SignUtil.checkSignature(account.getToken(), signature, timestamp, nonce)) {  
	            out.print(echostr);
	        } 
		}
		 
        out.close();  
        out = null;  
//		List<WeixinAccountEntity> weixinAccountEntities = weixinAccountService.getList(WeixinAccountEntity.class);
//		for (WeixinAccountEntity account : weixinAccountEntities) {
//			if (SignUtil.checkSignature(account.getAccounttoken(), signature,
//					timestamp, nonce)) {
//				try {
//					response.getWriter().print(echostr);
//					break;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
	}

	@RequestMapping(value = "/validate/{name}", method = RequestMethod.POST)
	public void wechatPost(HttpServletResponse response,
			HttpServletRequest request,
			@PathVariable("name") String name) throws IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8"); 
        
        //根据参数获取该注册用户的信息， 用于验证用户
		WxAccount account = wechatService.getWxAccountByWxId(name);
		if (account != null) {
			String respMessage = wechatService.coreService(request, account);
			
	//		System.out.println("canshu :  " + name);
			PrintWriter out = response.getWriter();
			out.print(respMessage);
			out.close();
		}
	}

}

package com.weixin.web.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.weixin.util.MediaUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixin.core.manager.ClientManager;
import com.weixin.core.util.JsonUtil;
import com.weixin.web.model.Client;
/**
 * 系统管理
 * @author zsx
 *
 */
@Controller
@RequestMapping("sys")
public class SystemController {
	/**
	 * 跳转到首页模块(第一项模块)
	 */
	@RequestMapping("toSystemManager")
	public String toSystemManager(){
		return "jsp/system/systemManager";
	}
	/**
	 * 跳转到公共号信息页面
	 */
	@RequestMapping("toaccountInformation")
	public String toaccountInformation(){
		return "jsp/system/accountInformation";
	}
	/**
	 * 跳转到在线用户展示页面
	 */
	@RequestMapping("toonlineList")
	public String toonlineList(){
		return "jsp/system/onlineList";
	}
	

	/**
	 * 在线用户列表
	 */
	@RequestMapping("/online")
	public void datagridOnline(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Client> clients = new ArrayList<Client>();
		clients.addAll(ClientManager.getInstance().getAllClient());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		response.getWriter().write(gson.toJson(clients));
		response.getWriter().close();

	}
	
	
	
}

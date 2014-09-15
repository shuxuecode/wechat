<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.weixin.web.entity.WxAccount"%>

<% 
	WxAccount account = (WxAccount)request.getAttribute("account");
%>

<div id="" class="information">账号信息 :</div>
<div id="" class="infor">
	<form id="Form" method="post">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th>公共号名称&nbsp;&nbsp;</th>
				<td><input type="text" name="name" value="<%=(account.getAccountname()==null)?"" : account.getAccountname()%>" placeholder="微信号"> <span
					class="remind">您的微信公共号名称</span></td>
			</tr>
			<tr>
				<th>公共号类型&nbsp;&nbsp;</th>
				<td>
					<select name="type">
						<%if(account.getType()!=null && account.getType() == 1){%>
							<option value="0">服务号</option>
							<option value="1" selected>订阅号</option>
						<%}else{%>
							<option value="0" selected>服务号</option>
							<option value="1">订阅号</option>
						<%} %>
					</select> 
					<span class="remind">订阅号需要微信认证后才能够获取开发者ID</span>
				</td>
			</tr>
			<tr>
				<th>原始id&nbsp;&nbsp;</th>
				<td><input type="text" name="wxid" value="<%=(account.getWxId()==null)?"":account.getWxId() %>" placeholder="公共平台提供的以gh_开头的">
				<span class="remind">请准确填写，这将作为您的唯一标示</span></td>
			</tr>
			<tr>
				<td colspan="2">
				<div style="height: 35px; border-bottom: 1px solid #ddd;"></div>
				</td>
			</tr>
			<tr>
				<th>开发者ID</th>
				<td></td>
			</tr>
			<tr>
				<th>AppId&nbsp;&nbsp;</th>
				<td><input type="text" name="appid" value="<%=(account.getAppid()==null)?"":account.getAppid() %>" placeholder="appid">
				<span class="remind">开发者模式下提供的AppId</span></td>
			</tr>
			<tr>
				<th>AppSecret&nbsp;&nbsp;</th>
				<td><input type="text" name="secret" value="<%=(account.getSecret()==null)?"":account.getSecret() %>" placeholder="secret">
				<span class="remind">开发者模式下提供的AppSecret</span></td>
			</tr>
		
		</table>
	</form>
</div>
<div class="">
<button class="button" onclick="save()">保存</button>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.weixin.web.entity.WxAccount"%>

<% 
	WxAccount account = (WxAccount)request.getAttribute("account");
%>
<div id="" class="information">账号信息 :</div>
<div id="" class="infor">
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<th>公共号名称&nbsp;&nbsp;</th>
		<td>
		<p class="show"><%=(account.getAccountname()==null)?"" : account.getAccountname()%></p>
		</td>
	</tr>
	<tr>
		<th>公共号类型&nbsp;&nbsp;</th>
		<td>
		<p class="show"><%=(account.getType()==null)?"":((account.getType()==0)?"服务号":"订阅号") %></p>
		</td>
	</tr>
	<tr>
		<th>原始id&nbsp;&nbsp;</th>
		<td>
		<p class="show"><%=(account.getWxId()==null)?"":account.getWxId() %></p>
		</td>
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
		<td>
		<p class="show"><%=(account.getAppid()==null)?"":account.getAppid() %></p>
		</td>
	</tr>
	<tr>
		<th>AppSecret&nbsp;&nbsp;</th>
		<td>
		<p class="show"><%=(account.getSecret()==null)?"":account.getSecret() %></p>
		</td>
	</tr>

</table>

</div>
<div class="">
<button class="button" onclick="update()">修改</button>
</div>
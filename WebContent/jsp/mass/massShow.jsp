<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>  
<%@page import="com.weixin.web.model.ArticleModel"%>
<%@page import="java.util.*"%>	    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/message/showMessage.css" type="text/css"/>
</head>
<body>
<div style="overflow:hidden;clear:both;">
	<div style="width:25%;margin: auto;">
		<div class="news">
			<div class="news_info">
				<em class="news_date"></em>
			</div>
			
<% 
	List<ArticleModel> list = (List)request.getAttribute("list");
%>
<!-- 没有图文时默认 -->
<% 
	if(list == null || list.size() == 0){
%>
		<div class="cover">
			<div class="item_title">
				<a href="javascript:void(0);">标题</a>
			</div>
			<div class="cover_img">
				<img class="imgcss" alt="" src="http://bcs.duapp.com/wechatimages/image/coverImage.jpg">
			</div>
		</div>
		<div class="item">
			<img class="imgcss" alt="" src="http://bcs.duapp.com/wechatimages/image/thumbnail.jpg">
			<h4 class="item_title">
				<a href="javascript:void(0);">小标题</a>
			</h4>
		</div>
<%
	}
%>	
<!-- 显示第一条 -->
<% 
	if(list != null && list.size() > 0){
%>
			<!-- 大图文 -->
			<div class="cover" >
				<div class="item_title">
					<a target="_blank" href="#"><%=list.get(0).getTitle() %></a>
				</div>
				<div class="cover_img">
					<img class="imgcss" alt="" src="<%=list.get(0).getThumbMediaUrl() %>">
				</div>
			</div>
<%
	}
%>	
<!-- 显示其他 -->
<% 
	if(list != null && list.size() > 1){
		for(int i=1; i < list.size(); i++){
%>		
			<!--  -->
			<div class="item" >
				<img class="imgcss" alt="" src="<%=list.get(i).getThumbMediaUrl() %>">
				<h4 class="item_title">
					<a target="_blank" href="#"><%=list.get(i).getTitle() %></a>
				</h4>
			</div>
<%
		}
	}
%>				
			
			
		</div>
	</div>
</div>


</body>
</html>
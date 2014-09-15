<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>  
<%@page import="com.weixin.web.entity.message.WxNewsitem"%>
<%@page import="java.util.*"%>	    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/message/showMessage.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/message/msgShow.js"></script>
</head>
<body>
<div style="overflow:hidden;clear:both;">
	<div style="float:left;width:25%;margin: 1% 1% 1% 1%;">
		<div class="news">
			<div class="news_info">
				<em class="news_date">${date }</em>
			</div>
			
<% 
	List<WxNewsitem> list = (List)request.getAttribute("list");
%>
<!-- 没有图文时默认 -->
<% 
	if(list.size() == 0){
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
	if(list.size() > 0){
%>
			<!-- 大图文 -->
			<div class="cover"  onmouseover="mouseover('cover')" onmouseout="mouseout('cover')">
				<div class="item_title">
					<a target="_blank" href="#"><%=list.get(0).getTitle() %></a>
				</div>
				<div class="cover_img">
					<img class="imgcss" alt="" src="<%=list.get(0).getPicUrl() %>">
				</div>
				<div class="mask cover_m" style="display: none">
					<a class="cover_edit" href="#" onclick="goUpate('<%=list.get(0).getId() %>')"></a>
				</div>
			</div>
<%
	}
%>	
<!-- 显示其他 -->
<% 
	if(list.size() > 1){
		for(int i=1; i < list.size(); i++){
%>		
			<!--  -->
			<div class="item" onmouseover="mouseover('item')" onmouseout="mouseout('item')">
				<div class="mask item_m" style="display: none">
					<a class="edit" id="edit" href="#" onclick="goUpate('<%=list.get(i).getId() %>')" ></a>
					<a class="dete" id="delete"  href="#" onclick="goDel('<%=list.get(i).getId() %>')"></a>
				</div>
				<img class="imgcss" alt="" src="<%=list.get(i).getPicUrl() %>">
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
	<div id="updateDiv" style="float:right;width:72%;margin-top: 1%;
				display:none;">
				<!-- 显示修改页面 -->
	</div>
</div>


</body>
<script type="text/javascript">
	function mouseover(symbol){
		if(symbol=='cover'){
			$(".cover_m").removeAttr("style");
			$(".item_m").attr("style","display:none");
		}else if(symbol=='item'){
		    $(".cover_m").attr("style","display:none");
			$(".item_m").removeAttr("style");
		}
	}
	
	function mouseout(symbol){
		if(symbol=='cover'){
			$(".cover_m").attr("style","display:none");
		}else if(symbol=='item'){
			$(".item_m").attr("style","display:none");
		}
	}
	
	function goUpate(newsId){
		var  url = '${webroot}/msg/updateNewsitem?id='+newsId;
		$.post(url, function(result) {
					$("#updateDiv").html(result);
		});
		$("#updateDiv")[0].style.display = 'block';
	}
	
	
	function goDel(newsId){
		$.messager.confirm(
				'提醒',
				'确定要删除吗？',
				function(b) {
					if (b) {  $.post("${webroot }/msg/delNewsitem",
								{"id" : newsId},
								function(data){
									console.log(data);
									if(data.success){
										//$('#dg').datagrid('load',{});
										$.messager.show({
											msg : data.msg,
											title : '提示'
										});
									}
									else{
										$.messager.show({
											msg : data.msg,
											title : '提示'
										});
									}
								});
		}});
	}

	function updateNews(){
		$.messager.show({
			msg : '正在更新, 请稍后 ...',
			title : '提示',
			timeout:5000
		});
		var fd = document.getElementById("update_news");
        var formData = new FormData(fd);
        $.ajax({
            url: '${webroot}/msg/updNewsitem',
            type: 'POST',
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function(result){
        				console.log(result);
        				if(result.success){
            				//
        			        refresh();
        			        $.messager.show({
        						msg : result.msg,
        						title : '提示'
        					});
        				}
        				else{
        					$.messager.show({
        						msg : result.msg,
        						title : '提示'
        					});
        				}
        			},
            dataType:'json'
        });

		
	}
	
	
	function refresh(){
		 //location.reload(); 
		 parent.document.getElementById('newsframe').src = parent.document.getElementById('newsframe').src;
	}
	
</script>
</html>
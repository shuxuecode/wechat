<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.weixin.web.model.Tree"%>
<%@page import="java.util.*"%>			
<% 
	Map map = (Map)request.getAttribute("map");
	List<Tree> list = (List)map.get("list");
	List<Tree> subMenus = new ArrayList();
%>
				<%for(int i=0;i<list.size();i++){ %>
				<li class="secondMenu">
					<div onclick="getMainMenu(this)" onmouseover="showDel(this,true)" onmouseout="showDel(this,false)" class="shu">
						<a href="javascript:void(0);" style="margin-left: 10px;">
							<%=list.get(i).getText() %>
						</a>
						<a style="display:none;"><%=list.get(i).getId() %></a>
						<a style="display:none;"><%=list.get(i).getState() %></a>
						<a style="display:none;"><%=list.get(i).getAttributes().get("type") %></a>
						<% if((Integer)list.get(i).getAttributes().get("type") == 0){ %>
							<a style="display:none;"></a>
							<a style="display:none;"></a>
						<% }else if((Integer)list.get(i).getAttributes().get("type") == 1){ %>
							<a style="display:none;"><%=list.get(i).getAttributes().get("keyWordId") %></a>
							<a style="display:none;"><%=list.get(i).getAttributes().get("keyWord") %></a>
						<% }else if((Integer)list.get(i).getAttributes().get("type") == 2){ %>
							<a style="display:none;"><%=list.get(i).getAttributes().get("webName") %></a>
							<a style="display:none;"><%=list.get(i).getAttributes().get("webUrl") %></a>
						<% }else if((Integer)list.get(i).getAttributes().get("type") == 3){ %>
							<a style="display:none;"><%=list.get(i).getAttributes().get("url") %></a>
							<a style="display:none;"></a>
						<% } %>
						<a onclick="del(this,'<%=list.get(i).getId() %>');" style="float:right; margin:5px 5px 0 0; visibility:hidden;">
							<img src="http://bcs.duapp.com/wechatimages/image/menu_delete.png">
						</a>
					</div>
					<dl class="subMenus" style="display: none;">
						
						<dd class="add"  onclick="newSubMenu(this)">
							<a href="javascript:void(0);">
									新增菜单
							</a>
							<a style="display:none;"><%=list.get(i).getId() %></a>
						</dd>
						<%
						if(map.get(list.get(i).getId()) != null){
							subMenus = (List<Tree>)map.get(list.get(i).getId());
							for(int n=0;n<subMenus.size();n++){
						%>
						<dd onclick="getSubMenu(this)" onmouseover="showDel(this,true)" onmouseout="showDel(this,false)">
							<a href="javascript:void(0);" style="display: block;
    width: 75px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
						    	<%=subMenus.get(n).getText()%>
						    </a>
							<a style="display:none;"><%=subMenus.get(n).getId() %></a>
							<a style="display:none;"><%=subMenus.get(n).getAttributes().get("type") %></a>
							<% if((Integer)subMenus.get(n).getAttributes().get("type") == 0){ %>
								<a style="display:none;"></a>
								<a style="display:none;"></a>
							<% }else if((Integer)subMenus.get(n).getAttributes().get("type") == 1){ %>
								<a style="display:none;"><%=subMenus.get(n).getAttributes().get("keyWordId") %></a>
								<a style="display:none;"><%=subMenus.get(n).getAttributes().get("keyWord") %></a>
							<% }else if((Integer)subMenus.get(n).getAttributes().get("type") == 2){ %>
								<a style="display:none;"><%=subMenus.get(n).getAttributes().get("webName") %></a>
								<a style="display:none;"><%=subMenus.get(n).getAttributes().get("webUrl") %></a>
							<% }else if((Integer)subMenus.get(n).getAttributes().get("type") == 3){ %>
								<a style="display:none;"><%=subMenus.get(n).getAttributes().get("url") %></a>
								<a style="display:none;"></a>
							<% } %>
							<a onclick="del(this,'<%=subMenus.get(n).getId() %>');" style="float: right; margin: -36px 3px 0px 0px; visibility: hidden;">
								<img src="http://bcs.duapp.com/wechatimages/image/menu_delete.png">
							</a>
							</dd>
						<%}} %>
					</dl>
				</li>
				<%} %>
				
				<!--  -->
				<%  if(list.size() < 3){ %>
				<li class="secondMenu">
					<div onclick="newMainMenu()" class="shu"  style="background-color: #56AF45;">
						<a href="javascript:void(0);" >
							新增菜单
						</a>
					</div>
				</li>
				<%} %>




<script type="text/javascript">
function stopPropagation(e) {  
    e = e || window.event;  
    if(e.stopPropagation) { //W3C阻止冒泡方法  
        e.stopPropagation();  
    } else {  
        e.cancelBubble = true; //IE阻止冒泡方法  
    }  
}

function del(obj,id){
	obj.onclick = function(e) { 
		console.log(id); 
		var ret = window.confirm("确定删除该菜单吗？");
		if (ret) {
			$.post("${pageContext.request.contextPath }/menu/delmenu",
					{"id" : id},
					function(data){
						console.log(data);
						if(data.success){
							//
							$.post("${pageContext.request.contextPath }/menu/mymenu",
									function(data){
										$(".allMenus").html(data);
									}
							);
						}
						$.messager.show({
							msg : data.msg,
							title : '提示'
						});
					}
			);
		}  
		stopPropagation(e);  
	};
}

function showDel(obj,showOrHide){
	var aObj = obj.getElementsByTagName("a");
	aObj[aObj.length - 1].style.visibility = showOrHide ? 'visible' : 'hidden';
	
}

</script>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="在线用户列表">
		<div class="easyui-layout" data-options="fit:true">
			<!-- main数据列 -->
			<div class="center" data-options="region:'center'" style="height:100%; width:100%; margin:0px;">
				<table id="dg" class="easyui-datagrid" 
                    	data-options="url : '${webroot }/sys/online',queryParams:{},
                    	fit:true, pagination:true, pageSize:10, fitColumns:true,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:false,striped:true,
                               sortName:'subscribeTime', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',editor:'text',hidden:true,
											formatter: function(value,row,index){return row.user.id;}">id</th>
							<th data-options="field:'userName',width:100,align:'center',editor:'text',
											formatter: function(value,row,index){return row.user.userName;}">用户名</th>
							<th data-options="field:'userNumber',width:50,align:'center',editor:'text',
											formatter: function(value,row,index){return row.user.userNumber;}">用户编号</th>
							<th data-options="field:'privileges',width:250,align:'center'">访问权限</th>
							<th data-options="field:'ip',width:100,align:'center',editor:'text'">登录IP</th>
							<th data-options="field:'logindatetime',width:80,align:'center',editor:'text'">登录时间</th>
						</tr>
					</thead>
				</table>
			
			</div>
		</div>
	
	</div>
</div>

<!-- 遮罩层 -->
<jsp:include page="/jsp/layout/loading.jsp"></jsp:include>
<div>
	<input type="hidden" id="" value=""/>
</div>
</body>
</html>






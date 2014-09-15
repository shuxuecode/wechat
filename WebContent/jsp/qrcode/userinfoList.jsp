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
	<div title="关注者列表">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:28px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 查询列 -->
        		<div class="" id=""  >
        			<table>
	                	<tr>
	                        <td>起始时间：</td>
	                        <td><input name="activestarttime" class="easyui-datetimebox easyui-validatebox" style="width: 140px;"/></td>
	                        <td>结束时间：</td>
	                        <td><input name="activeendtime" class="easyui-datetimebox easyui-validatebox" style="width: 140px;"/></td>
	                        <td><input name="" id="query" type="button" value="查询" /></td>
	                    </tr>
                	</table>
        		</div>
			</div>
			<!-- main数据列 -->
			<div class="center" data-options="region:'center'" style="height:100%; width:100%; margin:0px;">
				<table id="dg" class="easyui-datagrid" 
                    	data-options="url : '${webroot }/qrcode/userinfoDatagrid',queryParams:{},
                    	fit:true, pagination:true, pageSize:10, fitColumns:true,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                               sortName:'subscribeTime', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',editor:'text',hidden:true">id</th>
							<th data-options="field:'headimgurl',width:80,align:'center',formatter:optFormatter">头像</th>
							<th data-options="field:'nickname',width:80,align:'center',editor:'text'">关注者昵称</th>
							<th data-options="field:'subscribeTime',width:100,align:'center',editor:'text'">关注时间</th>
							<th data-options="field:'group',width:100,align:'center',editor:'text'">分组信息</th>
							<th data-options="field:'other',width:160,align:'center',editor:'text'">备注</th>
							<th data-options="field:'sexName',width:40,align:'center'">性别</th>
							<th data-options="field:'country',width:50,align:'center',editor:'text'">国家</th>
							<th data-options="field:'province',width:50,align:'center',editor:'text'">省份</th>
							<th data-options="field:'city',width:50,align:'center',editor:'text'">城市</th>
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
	<input type="hidden" id="add_url" value="${webroot}"/>
	
</div>
</body>
<script>
function optFormatter(value,row,index){
	return '&nbsp;<img src="' + row.headimgurl + '" style="height:60px;" alt="暂无头像" />&nbsp;';
}
</script>
</html>






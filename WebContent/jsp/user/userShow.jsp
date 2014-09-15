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
	<div title="用户基础信息">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:65px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 功能列 -->
				<div class="sub">
					<table border="0" cellspacing="0" cellpadding="0" class="sub_nav">
                  		<tr>
		                    <td><a href="javascript:void(0)" onclick="$('#tg').treegrid('collapseAll');"><img src="${webroot }/images/layout/db.png"/>&nbsp;折叠</a></td>
		                    <td><a href="javascript:void(0)" onclick="$('#tg').treegrid('expandAll');"><img src="${webroot }/images/layout/db.png"/>&nbsp;展开</a></td>
                  		</tr>
                	</table>
				</div>
				<!-- 查询列 -->
        		<div class="sub2" title="查询条件" id=""  >
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
				<table id="tg" class="easyui-treegrid" title=""
						data-options="url: '${pageContext.request.contextPath }/user/getUserList',
									rownumbers: true,animate: true,
									fitColumns: true,method: 'get',
									idField: 'id',treeField: 'userName', fit: true,
									pagination: true, pageSize: 5, pageList: [5,10,15],
									onLoadSuccess: function (row, data) {
												$.each(data, function (i, val) {
												$('#tg').treegrid('collapseAll', data[i].id);});}
									">
					<thead>
						<tr>
							<th data-options="field:'id',width:10,editor:'text',hidden:true">id</th>
							<th data-options="field:'userName',width:80,align:'left',editor:'text'">用户名</th>
							<th data-options="field:'userNumber',width:80,editor:'text'">用户编号</th>
							<th data-options="field:'roleIds',width:10,hidden:true">角色id</th>
							<th data-options="field:'roleNames',width:150,editor:'text'">角色名称</th>
							<th data-options="field:'opt',width:250,formatter:optFormatter">操作</th>
						</tr>
					</thead>
				</table>
			
			</div>
		</div>
	
	</div>
</div>
<!-- ------弹出层-----------  -->
<div id="updrole" class="easyui-window" title="用户基础信息" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:500px;height:320px;">
	<form id="Form1" method="post" enctype="multipart/form-data">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">用户名:</td>
	            <td align="left"><input name="userName" id="userName" disabled="disabled" style="width:220px;"/>
	            				 <input name="userId" id="userId" type="hidden">
	            </td>
			</tr>
			<tr>
			   	<td align="right">分配角色:</td>
	            <td align="left"><input id="roleIds" style="width:220px;"></td>
			</tr>
		</table>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="update()">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#updrole').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>

</div>

<!-- 遮罩层 -->
<div id="fade" class="black_overlay"></div>
<div>
	<input type="hidden" id="url" value="${webroot }/user/addUser"/>
</div>
</body>
<script>
function optFormatter(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="updaterole(&quot;'+row.id+'&quot;,&quot;'+row.userName+'&quot;,&quot;'+row.roleIds+'&quot;)">角色管理</a>&nbsp; '
	+'';
}

function updaterole(id,name,roleid){
	$('#roleIds').combotree({
			url : '${pageContext.request.contextPath}/user/getRoleTree?userId=' + id,
			lines : true,
			animate : true,
			multiple:true,
			panelHeight : 'auto'
	});
	$('#userId').val(id);
	$('#userName').val(name);
	$('#updrole').window('open');
	$('#updrole').window('center');
}

function update(){
	var roleIds = $('#roleIds').combotree('getValues');
	
	var fd = document.getElementById("Form1");
    var formData = new FormData(fd);
    formData.append('roleIds' , roleIds);
    $.ajax({
        url: "${webroot}/user/updUserRole",
        type: 'POST',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function(result){
		        	console.log(result);
					if(result.success){
						$('#updrole').window('close');
						$("#tg").treegrid('reload');
					}
			        $.messager.show({
						msg : result.msg,
						title : '提示'
					});
    			},
        dataType:'json'
    });
}

</script>
</html>






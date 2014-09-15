<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/user/roleShow.js"></script>
</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="角色管理">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:30px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 功能列 -->
				<div class="sub">
					<table border="0" cellspacing="0" cellpadding="0" class="sub_nav">
                  		<tr>
		                    <td><a href="#" id="add_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;新增</a></td>
                  		</tr>
                	</table>
				</div>
			</div>
			
			<!-- main数据列 -->
			<div class="center" data-options="region:'center'" style="height:100%; width:100%; margin:0px;">
				<table id="dg" class="easyui-datagrid" 
                    	data-options="url : '${webroot }/user/getRoleList',queryParams:{},
                    	fit:true, pagination:true, pageSize:10,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                               sortName:'roleNumber', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'id',width:10,editor:'text',hidden:true">id</th>
							<th data-options="field:'roleNumber',width:80,editor:'text'">角色编号</th>
							<th data-options="field:'roleName',width:120,editor:'text'">角色名称</th>
							<th data-options="field:'opt',width:250,formatter:optFormatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>
	
	</div>
</div>
<!-- ------新增弹出层-----------  -->
<div id="add" class="easyui-window" title="基础信息" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:550px;height:360px;">
	<form id="Form1" method="post" enctype="multipart/form-data">
		<table id="add_table" border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">角色名称:</td>
	            <td align="left"><input type="text" name="name" class=""></td>
	        </tr>
	        <tr>
				<td align="right">角色编号:</td>
	            <td align="left"><input type="text" name="num" class=""></td>
			</tr>
		</table>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" id="toSave">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#add').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>

</div>

<!-- ------upd弹出层-----------  -->
<div id="upd" class="easyui-window" title="" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:550px;height:360px;">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">角色名称:</td>
	            <td align="left">
	            	<input type="text" id="name" >
	            	<input type="hidden" id="roleid">
	            </td>
	        </tr>
	        <tr>
				<td align="right">角色编号:</td>
	            <td align="left"><input id="num" ></td>
			</tr>
		</table>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="update()">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#upd').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>
</div>

<!-- ------权限管理弹出层-----------  -->
<div id="pr" class="easyui-window" title="权限管理" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<input type="hidden" id="modifyRP" value="" />
	<div style="margin: 10px 100px;">
		<button type="button" onclick="allCheck(true);">全选</button>
		<button type="button" onclick="allCheck(false);">反选</button>
	</div>
	
	<ul id="prTree" style="margin: 15px 100px;"></ul>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;border-top: 2px solid #ccc;padding-top: 25px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveRP()">
	        		<span style="font-size:18px">保存</span>
	        	</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#pr').window('close')">
	        		<span style="font-size:18px">关闭</span>
	        	</a>
	        </td>
	    </tr>
    </table>
</div>

<!-- 遮罩层 -->
<jsp:include page="/jsp/layout/loading.jsp"></jsp:include>
<div>
	<input type="hidden" id="add_url" value="${webroot }/user/addRole"/>
	
</div>
</body>
<script>
function optFormatter(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="propt(&quot;'+row.id+'&quot;)">权限管理</a>&nbsp; |'
	+' &nbsp;<a href="#" onclick="upd(&quot;'+row.id+'&quot;,&quot;'+row.roleName+'&quot;,&quot;'+row.roleNumber+'&quot;)">修改角色</a>&nbsp;&nbsp; |' 
	+' &nbsp;<a href="#" onclick="del(&quot;'+row.id+'&quot;)">删除角色</a>&nbsp;&nbsp; ';
}

function propt(id){
	$('#prTree').tree({    
	    url:'${webroot}/user/getRolePrivilegeTree?roleId=' + id,
	    method:'POST',
	    animate : true,
	    checkbox : true,
	    lines : true
	});
	$('#modifyRP').val(id);
	$('#pr').window('open');
}

function upd(id,name,num){
	$('#roleid').val(id);
	$('#name').val(name);
	$('#num').val(num);
	$('#upd').window('open');
	$('#upd').window('center');
}

function update(){
	$.post("${webroot }/user/updRole",
			{	"id" : $('#roleid').val(),
				"name" : $('#name').val(),
				"num" : $('#num').val()
			},
			function(data){
				console.log(data);
				if(data.success){
					$('#dg').datagrid('reload');
					$('#upd').window('close');
				}
				$.messager.show({
					msg : data.msg,
					title : '提示'
				});
		},'JSON');
}

function del(obj){
	$.messager.confirm(
			'提醒',
			'确定要删除吗？',
			function(b) {
				if (b) {  $.post("${webroot }/user/delRole",
							{"id" : obj},
							function(data){
								console.log(data);
								if(data.success){
									$('#dg').datagrid('load',{});
								}
								$.messager.show({
									msg : data.msg,
									title : '提示'
								});
							});
	}});
}

function saveRP(){
	$.post("${webroot }/user/updateRolePrivilege",
			{"roleId" : $('#modifyRP').val() , "ids" : getChecked()},
			function(data){
				console.log(data);
				if(data.success){
					$('#pr').window('close');
				}
				$.messager.show({
					msg : data.msg,
					title : '提示'
				});
			},'JSON');
}

function allCheck(obj){
	var roots = $('#prTree').tree('getRoots');//返回tree的所有根节点数组  
    if (obj) {  
        for ( var i = 0; i < roots.length; i++) {  
            var node = $('#prTree').tree('find', roots[i].id);//查找节点  
            $('#prTree').tree('check', node.target);//将得到的节点选中  
        }  
    } else {  
        for ( var i = 0; i < roots.length; i++) {  
            var node = $('#prTree').tree('find', roots[i].id);  
            $('#prTree').tree('uncheck', node.target);  
        }  
    }  
}

function getChecked(){
	var nodes = $('#prTree').tree('getChecked');
	var s = '';
	for(var i=0; i<nodes.length; i++){
	if (s != '') s += ',';
		s += nodes[i].id;
	}
	return s;
}

</script>
</html>






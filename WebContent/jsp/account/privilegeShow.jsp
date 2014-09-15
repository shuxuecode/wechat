<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/user/userShow.js"></script>

</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="权限列表">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:28px; width:100%; margin:0px; overflow:hidden; border:none">
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
						data-options="url: '${pageContext.request.contextPath }/user/getPrivilegeTree',
									rownumbers: true,animate: true,
									fitColumns: true,method: 'get',
									idField: 'id',treeField: 'text', fit: true,
									pagination: true, pageSize: 5, pageList: [5,10,15]
									">
					<thead>
						<tr>
							<th data-options="field:'id',width:10,editor:'text',hidden:true">id</th>
							<th data-options="field:'text',width:80,align:'left',editor:'text'">名称</th>
							<th data-options="field:'iconCls',width:120,editor:'text'">URL</th>
							<th data-options="field:'opt',width:200,editor:'text',formatter:optFormatter">操作</th>
						</tr>
					</thead>
				</table>
			
			</div>
		</div>
	
	</div>
</div>
<!-- ------新增弹出层-----------  -->
<div id="add" class="easyui-window" title="添加" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:500px;height:320px;">
	<form id="Form_add" method="post" enctype="multipart/form-data">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">权限名称:</td>
	            <td align="left"><input name="add_name" id="add_name" ></td>
			</tr>
			<tr>
			   	<td align="right">权限URL:</td>
	            <td align="left"><input name="add_url" id="add_url"></td>
			</tr>
			<tr>
				<td align="right">所属分类:</td>
				<td align="left">
					<input name="add_pid" id="add_pid" class="easyui-combobox"  data-options="
						valueField:'id',textField:'text',url:'${webroot }/user/getParentPrivilegeTree'" />
			    </td>
			</tr>
		</table>
	</form>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#add').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>

</div>

<!-- ------修改弹出层-----------  -->
<div id="upd" class="easyui-window" title="添加" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:500px;height:320px;">
	<form id="Form_upd" method="post" enctype="multipart/form-data">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<input type="hidden" id="upd_id" name="upd_id" value="" />
			<tr>
			   	<td align="right">权限名称:</td>
	            <td align="left"><input name="upd_name" id="upd_name" ></td>
			</tr>
			<tr>
			   	<td align="right">权限URL:</td>
	            <td align="left"><input name="upd_url" id="upd_url"></td>
			</tr>
			<tr>
				<td align="right">所属分类:</td>
				<td align="left">
					<input name="upd_pid" id="upd_pid" class="easyui-combobox"  data-options="
						valueField:'id',textField:'text',url:'${webroot }/user/getParentPrivilegeTree'" />
			    </td>
			</tr>
		</table>
	</form>
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

<!-- 遮罩层 -->
<div id="fade" class="black_overlay"></div>
<div>
	<input type="hidden" id="" value=""/>
</div>
</body>
<script>
function optFormatter(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="add()">新增</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="upd(&quot;'+row.id+'&quot;,&quot;'+row.text+'&quot;,&quot;'+row.iconCls+'&quot;)">修改</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="del(&quot;'+row.id+'&quot;)">删除</a>&nbsp;&nbsp; ';
}

function add(){
	$('#add_name').val('');
	$('#add_url').val('');
	$('#add_pid').combobox('reload');      
	$('#add').window('open');
	$('#add').window('center');
}

function save(){
    var formData = new FormData(document.getElementById("Form_add"));
    $.ajax({
        url: '${webroot }/user/addPrivilege',
        type: 'POST',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function(result){
		        	console.log(result);
					if(result.success){
						$('#add').window('close');
						$('#tg').treegrid('reload');
					}
			        $.messager.show({
						msg : result.msg,
						title : '提示'
					});
    			},
        dataType:'json'
    });
}

function upd(id,text,url){
	var node = $('#tg').treegrid('getParent',id);
	if(node == undefined || node == null){
		$.messager.alert('提示','暂时不支持修改菜单项','info');
		return;
	}
	$('#upd_id').val(id);
	$('#upd_name').val(text);
	$('#upd_url').val(url);
	$('#upd_pid').combobox('reload');  
	$('#upd_pid').combobox('setValue', node.id);
	$('#upd').window('open');//
	$('#upd').window('center');//
}

function update(){
	var fd = document.getElementById("Form_upd");
    var formData = new FormData(fd);
    $.ajax({
        url: '${webroot }/user/updPrivilege',
        type: 'POST',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function(result){
		        	console.log(result);
					if(result.success){
						$('#upd').window('close');
						$('#tg').treegrid('reload');
					}
			        $.messager.show({
						msg : result.msg,
						title : '提示'
					});
    			},
        dataType:'json'
    });
}

function del(obj){
	$.messager.confirm(
			'提醒',
			'确定要删除吗？',
			function(b) {
				if (b) {  $.post("${webroot }/user/delPrivilege",
							{"id" : obj},
							function(data){
								console.log(data);
								if(data.success){
									$('#tg').treegrid('reload');
								}
								$.messager.show({
									msg : data.msg,
									title : '提示'
								});
							});
	}});
}
</script>
</html>






<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/message/msgShow.js"></script>
</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="公共号管理">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:65px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 功能列 -->
				<div class="sub">
					<table border="0" cellspacing="0" cellpadding="0" class="sub_nav">
                  		<tr>
		                    <!--  
		                    <td><a href="#" id="add_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;新增</a></td>
		                    -->
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
				<table id="dg" class="easyui-datagrid" 
                    	data-options="url : '${webroot }/account/datagrid',queryParams:{},
                    	fit:true, pagination:true, pageSize:10,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:false,striped:true,
                               sortName:'msg', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'accountId',width:10,editor:'text',hidden:true">id</th>
							<th data-options="field:'userName',width:80,editor:'text'">账户名</th>
							<th data-options="field:'accountname',width:80,align:'left',editor:'text'">公共号名称</th>
							<th data-options="field:'accounttype',width:70,editor:'text'">公共号类别</th>
							<th data-options="field:'url',width:255,editor:'text'">URL地址</th>
							<th data-options="field:'token',width:80,editor:'text'">Token</th>
							<th data-options="field:'appid',width:130,editor:'text'">Appid</th>
							<th data-options="field:'secret',width:210,editor:'text'">Appsecret</th>
							<th data-options="field:'wxId',width:120,editor:'text'">原始ID</th>
						</tr>
					</thead>
				</table>
			
			</div>
		</div>
	
	</div>
</div>
<!-- ------新增弹出层-----------  -->
<div id="add" class="easyui-window" title="" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:550px;height:360px;">
	<form id="Form1" method="post" enctype="multipart/form-data">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">:</td>
	            <td align="left"><input name="title" class=""></td>
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

<!-- 遮罩层 -->
<div id="fade" class="black_overlay"></div>
<div>
	<input type="hidden" id="add_url" value="${webroot }"/>
</div>
</body>
</html>






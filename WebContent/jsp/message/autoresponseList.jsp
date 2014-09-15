<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/message/autoresponse.js"></script>
</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="申请基础信息">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:65px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 功能列 -->
				<div class="sub">
					<table border="0" cellspacing="0" cellpadding="0" class="sub_nav">
                  		<tr>
		                    <!--查看代办任务-->
		                    <td><a href="#" id="add_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;新增</a></td>
		                    <td><a href="#" id="upd_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;修改</a></td>
		                    <td><a href="#" id="del_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;删除</a></td>
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
                    	data-options="url : '${webroot }/msg/autoresponseDatagrid',queryParams:{},
                    	fit:true, pagination:true, pageSize:10,fitColumns:true,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:false,striped:true,
                               sortName:'addTime', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',width:10,editor:'text',hidden:true">id</th>
							<th data-options="field:'keyword',width:80,align:'center',editor:'text'">关键词</th>
							<th data-options="field:'msgType',width:80,editor:'text'">回复消息类型</th>
							<th data-options="field:'resTemplateId',hidden:true">模板id</th>
							<th data-options="field:'templateName',width:80,editor:'text'">模板名称</th>
							<th data-options="field:'addTime',width:60,editor:'text'">添加时间</th>
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
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">关键词:</td>
	            <td align="left"><input name="keyword" class="" style="width:155px;"></td>
			</tr>
			<tr>
				<td align="right">回复消息类型:</td>
			 	<td align="left">
			 		<input id="msgType" name="msgType" class="easyui-combobox"  style="width:155px;" />
			 	</td>
			</tr>
			<tr>
				<td align="right">选择模板:</td>
			 	<td align="left" >
			 		<input id="template" name="template" class="easyui-combobox"  
			 		data-options="valueField:'cvalue',textField:'ctext',panelHeight:'auto'"  style="width:155px;" />
			 	</td>
			</tr>
		</table>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 50px;">
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
	<input type="hidden" id="getTemplate_url" value="${webroot }/msg/getTemplateByMsgtype"/>
	<input type="hidden" id="add_url" value="${webroot }/msg/addAutoresponse"/>
	<input type="hidden" id="del_url" value="${webroot }/msg/delAutoresponse"/>
	
</div>
</body>
</html>






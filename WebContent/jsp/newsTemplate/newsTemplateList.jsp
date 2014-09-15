<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/message/newstemplate.js"></script>
</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="图文消息模板">
		<div class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:65px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 功能列 -->
				<div class="sub">
					<table border="0" cellspacing="0" cellpadding="0" class="sub_nav">
                  		<tr>
		                    <td><a href="#" id="add_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;新增</a></td>
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
                    	data-options="url : '${webroot }/msg/newstemplateDatagrid',queryParams:{status:0},
                    	fit:true, pagination:true, pageSize:10, fitColumns:true,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:false,striped:true,
                               sortName:'msg', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',editor:'text',hidden:true">id</th>
							<th data-options="field:'templateName',width:200,align:'center',editor:'text'">模板名称</th>
							<th data-options="field:'addTime',width:100,editor:'text'">添加时间</th>
							<th data-options="field:'opt',width:200,editor:'text',formatter:optFormatter">操作</th>
						</tr>
					</thead>
				</table>
			
			</div>
		</div>
	
	</div>
</div>

<!-- ------新增弹出层-----------  -->
<div id="add" class="easyui-window" title="基础信息" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:550px;height:360px;">
	<form id="Form" method="post" enctype="multipart/form-data">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">消息标题:</td>
	            <td align="left"><input id="tempName" type="text"></td>
			</tr>
		</table>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" id="toSave_temp">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#add').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>

</div>

<!-- ------新增图文弹出层-----------  -->
<div id="add_news" class="easyui-window" title="添加图文" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<form id="Form_news" method="post" enctype="multipart/form-data">
		<input type="hidden" id="tempId" name="tempId" value=""/>
		<div style="">
			<jsp:include page="/jsp/newsTemplate/article.jsp"></jsp:include>
		</div>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin: 20px 0 40px 0;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" id="toSave_news">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#add_news').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>
</div>


<!-- ------查看弹出层-----------  -->
<div id="see" class="easyui-window" title="图文消息" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	
	<iframe scrolling="auto" id="newsframe" src="" frameborder="0" 
		style="width:100%;height:520px;">
	</iframe>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;border-top: 2px solid #ccc;padding-top: 25px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#see').window('close')">
	        		<span style="font-size:18px">关闭</span>
	        	</a>
	        </td>
	    </tr>
    </table>
</div>


<!-- 遮罩层 -->
<jsp:include page="/jsp/layout/loading.jsp"></jsp:include>
<div>
	<input type="hidden" id="add_temp_url" value="${webroot }/msg/addNewstemplate"/>
	<input type="hidden" id="add_news_url" value="${webroot }/msg/addWxNewsitem"/>
	<input type="hidden" id="refuse_url" value="${webroot }/msg/refuse"/>
	
</div>
</body>
<script>
function optFormatter(value,row,index){

	return '&nbsp;&nbsp;<a href="#" onclick="see(&quot;'+row.id+'&quot;,&quot;'+row.addTime+'&quot;)">查看</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="add_news(&quot;'+row.id+'&quot;)">添加图文</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="del(&quot;'+row.id+'&quot;)">删除</a>&nbsp;&nbsp; ';
}

function see(id,date){
	document.getElementById("newsframe").src = '${webroot }/msg/showMessage?id=' + id + '&date=' + date;
	$('#see').window('open');
}
function add_news(id){
	$('#tempId').val(id);
	emptyAll("#add_news_table");
	document.getElementById("img_prev").src = "http://bcs.duapp.com/wechatimages/image/coverdefault.jpg";
	$('#add_news').window('open');
}

function del(obj){
	$.messager.confirm(
			'提醒',
			'确定要删除吗？',
			function(b) {
				if (b) {  $.post("${webroot }/msg/delNewstemplate",
							{"id" : obj},
							function(data){
								console.log(data);
								if(data.success){
									$('#dg').datagrid('load',{});
									$.messager.show({
										msg : data.msg,
										title : '提示'
									});
								}
							});
	}});
}

//点击新增数据时清空弹出层残留的数据
function emptyAll(tableId){
	//清空所有input元素中的数据
	$(tableId+' input').each(function(){
		$(this).val('');
	});
	//清空所有textarea元素中的数据
	$(tableId+' textarea').each(function(){
		$(this).val('');
	});
}
</script>
</html>






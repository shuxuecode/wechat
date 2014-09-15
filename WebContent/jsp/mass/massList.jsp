<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 	<link rel="stylesheet" href="${webroot }/style/common/dataShow.css" type="text/css"/>
  	<script type="text/javascript" src="${webroot }/jslib/message/massList.js"></script>
  	<style>
  		#send_openid_btn{
  			padding: 6px 35px;
		    background-color: #51a351;
		    color: #fff;
		    border-radius: 6px;
		    cursor: pointer;
		    font-size: 13px;
		    border: 1px solid #bbb;
  		}
  	</style>
</head>
<body style="height:100%; width:100%; margin:0px;">
<div class="easyui-tabs" fit="true">
	<div title="群发消息">
		<div id="layout_div" class="easyui-layout" data-options="fit:true">
			<!-- 上部 -->
			<div class="center" data-options="region:'north'" style="height:65px; width:100%; margin:0px; overflow:hidden; border:none">
				<!-- 功能列 -->
				<div class="sub">
					<table border="0" cellspacing="0" cellpadding="0" class="sub_nav">
                  		<tr>
		                    <!--查看代办任务-->
		                    <td><a href="#" id="add_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;新增</a></td>
		                    <td><a href="#" id="upd_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;修改</a></td>
		                    <td><a href="#" id="del_btn"><img src="${webroot }/images/layout/db.png"/>&nbsp;批量删除</a></td>
		                    <td style="width:90px;"></td>
		                    <td><a href="#" id="group_send" style="width:180px;">&nbsp;&nbsp;&nbsp;&nbsp;根据分组群发</a></td>
		                    <td><a href="#" id="openid_send" style="width:180px;">&nbsp;&nbsp;&nbsp;&nbsp;根据用户群发</a></td>
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
                    	data-options="url : '${webroot }/msg/massDatagrid',queryParams:{},
                    	fit:true, pagination:true, pageSize:10, fitColumns:true,
                    	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                               sortName:'addTime', sortOrder:'desc',idField:'id'">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th data-options="field:'id',editor:'text',hidden:true">id</th>
							<th data-options="field:'massName',width:200,align:'center',editor:'text'">消息名称</th>
							<th data-options="field:'send',width:100,editor:'text'">是否已发送</th>
							<th data-options="field:'addTime',width:100,editor:'text'">添加时间</th>
							<th data-options="field:'opt',width:200,editor:'text',formatter:optFormatter">操作</th>
						</tr>
					</thead>
				</table>
			
			</div>
			<!-- 提示信息 -->
			<div  data-options="region:'south',title:'提示',split:true" style="width:100%;height:50px;" 
					onmouseover="$('#layout_div').layout('panel', 'south').panel('resize',{height:200});$('#layout_div').layout('resize');" 
					onmouseout="$('#layout_div').layout('panel', 'south').panel('resize',{height:50});$('#layout_div').layout('resize');">
			群发任务一般需要较长的时间才能全部发送完毕，请耐心等待。
			</div>
		</div>
	
	</div>
</div>

<!-- ------新增弹出层-----------  -->
<div id="add" class="easyui-window" title="新增群发图文信息" data-options="modal:true,closed:true,collapsible:false,minimizable:false,resizable: false" style="width:550px;height:360px;">
	<form id="Form" method="post" enctype="multipart/form-data">
		<table border="0" cellspacing="0" cellpadding="0" class="tab">
			<tr>
			   	<td align="right">消息名称:</td>
	            <td align="left"><input id="massName" type="text"></td>
			</tr>
		</table>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" id="toSave_mass">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#add').window('close')">关闭</a>
	        </td>
	    </tr>
    </table>

</div>

<!-- ------新增图文弹出层-----------  -->
<div id="add_news" class="easyui-dialog" title="添加图文" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<form id="Form_news" method="post" enctype="multipart/form-data">
		<input type="hidden" id="massId" name="massId" value=""/>
		<!-- 百度编辑器 -->
		<div id="ueditor" >
			<jsp:include page="addArticle.jsp"></jsp:include>
		</div>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin: 20px 0 40px 0;">
	    <tr>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" id="toSave_news">保存</a>
	        </td>
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#add_news').dialog('close')">关闭</a>
	        </td>
	    </tr>
    </table>
</div>


<!-- ------查看弹出层-----------  -->
<div id="see" class="easyui-window" title="图文消息" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<table id="dg1" class="easyui-datagrid" 
                 	data-options="url : '${webroot }/msg/articleDatagrid',
                 	fit:true, pagination:false, pageSize:10, fitColumns:true,
                 	loadMsg:'正在加载...',rownumbers:true,singleSelect:false,striped:true,
                            sortName:'addTime', sortOrder:'desc',idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',editor:'text',hidden:true">id</th>
				<th data-options="field:'thumbMediaUrl',width:200,align:'center',formatter:imgFormatter">缩略图</th>
				<th data-options="field:'title',width:100,editor:'text'">标题</th>
				<th data-options="field:'author',width:50,editor:'text'">作者</th>
				<th data-options="field:'content',width:100,editor:'text',formatter:nrFormatter">内容</th>
				<th data-options="field:'contentSourceUrl',width:100,editor:'text'">原文链接</th>
				<th data-options="field:'digest',width:100,editor:'text'">描述</th>
				<th data-options="field:'showCoverPic',width:100,editor:'text'">是否显示为封面</th>
				<th data-options="field:'sort',width:40,editor:'text'">排序</th>
				<th data-options="field:'addTime',width:100,editor:'text'">添加时间</th>
				<th data-options="field:'opt',width:200,editor:'text',formatter:optFormatter1">操作</th>
			</tr>
		</thead>
	</table>
	
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


<!-- ------预览弹出层-----------  -->
<div id="preview" class="easyui-window" title="群发消息" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false,fit: true" style="width:100%;height:100%;">
	<iframe scrolling="auto" id="newsframe" src="" frameborder="0" 
		style="width:100%;height:520px;">
	</iframe>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;border-top: 2px solid #ccc;padding-top: 25px;">
	    <tr ><td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#preview').window('close')">
	        		<span style="font-size:18px">关闭</span>
	        	</a>
	        </td></tr>
    </table>
</div>


<!-- ------按分组群发弹出层-----------  -->
<div id="send_group_div" class="easyui-window" title="分组群发" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<table id="dg_group" class="easyui-datagrid" 
                 	data-options="url : '${webroot }/msg/groupDatagrid',
                 	fit:true, pagination:false, pageSize:10, fitColumns:true,
                 	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                            sortName:'addTime', sortOrder:'desc',idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'id',editor:'text',hidden:true">id</th>
				<th data-options="field:'name',width:160,align:'center',editor:'text'">分组名称</th>
				<th data-options="field:'groupId',editor:'text',hidden:true">微信分组ID</th>
				<th data-options="field:'count',width:80,editor:'text'">用户数量</th>
				<th data-options="field:'opt',width:160,formatter:groupSendFormatter">群发操作</th>
			</tr>
		</thead>
	</table>
	<input type="hidden" id="group_send_mass_id" value="" />
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;border-top: 2px solid #ccc;padding-top: 25px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#send_group_div').window('close')">
	        		<span style="font-size:18px">关闭</span>
	        	</a>
	        </td>
	    </tr>
    </table>
</div>


<!-- ------按OPENID群发弹出层-----------  -->
<div id="send_openid_div" class="easyui-window" title="按用户群发" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<div style="margin: 10px 100px;">
		<button type="button" id="send_openid_btn">群发</button>
	</div>
	<table id="dg_openid" class="easyui-datagrid" 
                 	data-options="url : '${webroot }/qrcode/userinfoDatagrid',
                 	fit:true, pagination:false, pageSize:10, fitColumns:true,
                 	loadMsg:'正在加载...',rownumbers:true,singleSelect:false,striped:true,
                            sortName:'subscribeTime', sortOrder:'desc',idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',editor:'text',hidden:true">id</th>
				<th data-options="field:'headimgurl',width:80,align:'center',formatter:headimgFormatter">头像</th>
				<th data-options="field:'nickname',width:100,align:'center',editor:'text'">关注者昵称</th>
				<th data-options="field:'subscribeTime',width:100,align:'center',editor:'text'">关注时间</th>
				<th data-options="field:'group',width:100,align:'center',editor:'text'">分组信息</th>
				<th data-options="field:'opt',width:100,align:'center',editor:'text'"></th>
			</tr>
		</thead>
	</table>
	<input type="hidden" id="openid_send_mass_id" value="" />
	<table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin-top: 10px;border-top: 2px solid #ccc;padding-top: 25px;">
	    <tr >
	        <td align="center">
	        	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#send_openid_div').window('close')">
	        		<span style="font-size:18px">关闭</span>
	        	</a>
	        </td>
	    </tr>
    </table>
</div>



<!-- 遮罩层 -->
<jsp:include page="/jsp/layout/loading.jsp"></jsp:include>
<div>
	<input type="hidden" id="add_mass_url" value="${webroot }/msg/addmass"/>
	<input type="hidden" id="add_news_url" value="${webroot }/msg/addMassNews"/>
	<input type="hidden" id="del_mass_url" value="${webroot }/msg/delmass"/>
	<input type="hidden" id="send_group_url" value="${webroot }/mass/groupsend"/>
	<input type="hidden" id="send_openid_url" value="${webroot }/mass/openidsend"/>
	
</div>
</body>
<script>
function optFormatter(value,row,index){

	return '&nbsp;&nbsp;<a href="#" onclick="preview(&quot;'+row.id+'&quot;)">预览</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="see(&quot;'+row.id+'&quot;)">查看</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="add_news(&quot;'+row.id+'&quot;)">添加图文</a>&nbsp; |'
			+' &nbsp;<a href="#" onclick="del(&quot;'+row.id+'&quot;)">删除</a>&nbsp;&nbsp; ';
}

function nrFormatter(value,row,index){
	return row.content.substr(0,150);//row.content
}

function imgFormatter(value,row,index){
	return '&nbsp;<img src="' + row.thumbMediaUrl + '" style="height:80px;" alt="暂无图片" />&nbsp;';
}

function optFormatter1(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="alert(&quot;'+row.id+'&quot;)">修改</a>&nbsp;&nbsp; | '
	       + '&nbsp;&nbsp;<a href="#" onclick="del_article(&quot;'+row.id+'&quot;)">删除</a>&nbsp;&nbsp; ';
}

function groupSendFormatter(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="sendByGroupId(&quot;'+row.groupId+'&quot;)">群发给该组用户</a>&nbsp;&nbsp; ';
}

function headimgFormatter(value,row,index){
	return '&nbsp;<img src="' + row.headimgurl + '" style="height:60px;" alt="暂无头像" />&nbsp;';
}

function see(id){
	$('#dg1').datagrid('reload', { massId : id }); 
	$('#see').window('open');
}

function preview(id){
	document.getElementById("newsframe").src = '${webroot }/msg/massShow?id=' + id;
	$('#preview').window('open');
}

function add_news(id){
	$('#massId').val(id);
	emptyAll("#add_news_table");
	document.getElementById("img_prev").src = "http://bcs.duapp.com/wechatimages/image/coverdefault.jpg";
	$('#add_news').dialog('open');
}

function sendByGroupId(groupid){
	$('#send_group_div').window('close');
	$.post("${webroot }/mass/groupsend",
			{"massid" : $('#group_send_mass_id').val() , "groupid" : groupid},
			function(data){
				console.log(data);
				$.messager.show({
					msg : data,
					title : '提示'
				});
	});
}


function del(obj){
	$.messager.confirm(
			'提醒',
			'确定要删除吗？',
			function(b) {
				if (b) {  $.post("${webroot }/msg/delmass",
							{"ids" : obj},
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






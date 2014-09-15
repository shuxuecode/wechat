<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${webroot }/style/message/smart.css"/>
</head>
<body>
<div class="main">
	<div class="zhineng">
		智能回复设置
	</div>
	
	<div class="tab">
		<table border="0" cellspacing="0" cellpadding="0">
		  <tr>
		  	<th>开启关注时回复：</th>
		  	<td>
			  	<div class="switch demo3">
					<input id="gz" type="checkbox" onchange="checkchange()"  ${subscribe }>
					<label><i></i></label>
				</div>
			</td>
		  </tr>
		  
		  <tr>
		  	<th>关注时回复关键词：</th>
		  	<td>
			  	<input type="text" id="gz_keyword" value="${subscribekey }" disabled="disabled"/>
			  	<button onclick="$('#select_keyword').window('open')">选择</button>
			</td>
		  </tr>
		  
		  <tr>
		  	<th>开启默认无匹配回复：</th>
		  	<td>
			  	<div class="switch demo3">
					<input id="wu" type="checkbox" onchange="checkchange()"  ${nomatch }>
					<label><i></i></label>
				</div>
			</td>
		  </tr>
		  
		  <tr>
		  	<th>默认无匹配回复关键词：</th>
		  	<td>
			  	<input type="text" id="wu_keyword" value="${nomatchkey }" disabled="disabled"/>
			  	<button onclick="$('#select_keyword2').window('open')">选择</button>
			</td>
		  </tr>
		</table>
	  
	</div>
	
	<div class="okbtn">
		<button onclick="save()">确定</button>
	</div>
  
</div>

<!-- ------关注关键词弹出层-----------  -->
<div id="select_keyword" class="easyui-window" title="关键词列表" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<table id="dg" class="easyui-datagrid" 
                 	data-options="url : '${webroot }/msg/autoresponseDatagrid',
                 	fit:true, pagination:false, pageSize:10, fitColumns:true,
                 	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                            sortName:'addTime', sortOrder:'desc',idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'id',editor:'text',hidden:true">id</th>
				<th data-options="field:'keyword',width:200,align:'center',editor:'text'">关键词</th>
				<th data-options="field:'msgType',width:100,align:'center',editor:'text'">消息类型</th>
				<th data-options="field:'templateName',width:200,align:'center',editor:'text'">模板名称</th>
				<th data-options="field:'opt',width:200,editor:'text',formatter:optFormatter">操作</th>
			</tr>
		</thead>
	</table>
</div>

<!-- ------无匹配关键词弹出层-----------  -->
<div id="select_keyword2" class="easyui-window" title="关键词列表" 
	data-options="modal:true,closed:true,collapsible:false,draggable:false,maximized:true,
	minimizable:false,maximizable:false,resizable: false" style="width:100%;height:100%;">
	<table id="dg" class="easyui-datagrid" 
                 	data-options="url : '${webroot }/msg/autoresponseDatagrid',
                 	fit:true, pagination:false, pageSize:10, fitColumns:true,
                 	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                            sortName:'addTime', sortOrder:'desc',idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'id',editor:'text',hidden:true">id</th>
				<th data-options="field:'keyword',width:200,align:'center',editor:'text'">关键词</th>
				<th data-options="field:'msgType',width:100,align:'center',editor:'text'">消息类型</th>
				<th data-options="field:'templateName',width:200,align:'center',editor:'text'">模板名称</th>
				<th data-options="field:'opt',width:200,editor:'text',formatter:optFormatter2">操作</th>
			</tr>
		</thead>
	</table>
</div>

<div>
	<input type="hidden" id="gz_key_id" value="${subscribekeyid }"/>
	<input type="hidden" id="null_key_id" value="${nomatchkeyid }"/>
</div>
</body>
<script>
$(function () {
	
	
	

}); 
function optFormatter(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="fun1(&quot;'+row.id+'&quot;,&quot;'+row.keyword+'&quot;)">确定</a>&nbsp;&nbsp; ';
}
function fun1(id,key){ 
	$('#gz_key_id').val(id); 
	$('#gz_keyword').val(key); 
	$('#select_keyword').window('close'); 
}
function optFormatter2(value,row,index){
	return '&nbsp;&nbsp;<a href="#" onclick="fun2(&quot;'+row.id+'&quot;,&quot;'+row.keyword+'&quot;)">确定</a>&nbsp;&nbsp; ';
}
function fun2(id,key){ 
	$('#null_key_id').val(id); 
	$('#wu_keyword').val(key); 
	$('#select_keyword2').window('close'); 
}

function save(){
	var gzkey = '';
	var wukey = '';
	if(document.getElementById('gz').checked == true){
		gzkey = $('#gz_key_id').val();
	}
	if(document.getElementById('wu').checked == true){
		wukey = $('#null_key_id').val();
	}
	$.post('${pageContext.request.contextPath}/msg/updsmart', 
			{
				'key1' : gzkey,
				'key2' : wukey
			},
	        function(data) {
	            console.log(data);
	            $.messager.show({
					msg : data.msg,
					title : '提示'
				});
	        },
	        "json");
}

function checkchange(){
	var gz_check = document.getElementById('gz');
	var wu_check = document.getElementById('wu');
	//
	if(gz_check.checked == false){
		$('#gz_key_id').val(''); 
		$('#gz_keyword').val('');
	}
	if(wu_check.checked == false){
		$('#null_key_id').val(''); 
		$('#wu_keyword').val(''); 
	}
}
</script>
</html>
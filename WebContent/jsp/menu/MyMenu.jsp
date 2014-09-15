<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <link href="${webroot }/jslib/bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <script src="${webroot }/jslib/bootstrap-3.2.0-dist/js/bootstrap.min.js" type="text/javascript"></script>
  <link href="${webroot }/style/menu/style.css" rel="stylesheet" type="text/css">
  
  <script src="${webroot }/jslib/menu/menuTable.js" type="text/javascript"></script>

 </head>

 <body>
 <h3 class="popover-title">自定义菜单配置</h3>
 <div style="min-width:1200px;">
	<div id="iphone" style="border:0px solid;">
		<div id="iphone-view">
		</div>
		<div id="add-iphone-btn">
			<ul class="allMenus">
			<!-- 菜单展示区 -->
			</ul>
		</div>
		<div style="position: relative; left: 300px; top: 68px; width: 100px;">
			<a style="cursor: pointer; text-decoration: underline; color:blue;">
				点此调整菜单顺序
			</a>
		</div>
	</div>
	<!-- -->
	<div id="" class="" style="width:600px; height:653px; border:0px solid #bababa;float:left;">
		
		<!-- 提示信息和操作 -->
		<div style="padding: 0 10px;margin: 0px;">
			<div id="alertMsg">
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">
						×
					</button>
					<strong>
						提示!
					</strong>
					&nbsp;&nbsp; 主菜单最多4个汉字，子菜单最多7个汉字，多出来的部分将会以”...”代替，同步自定义菜单后，由于微信客户端缓存，最长需要24小时微信客户端才会展现出来，建议尝试取消关注公众账号后再次关注，则可以看到创建后的效果
				</div>
			</div>
			<div class="popover popover-static">
				<h3 class="popover-title">
					自定义菜单设置
				</h3>
				<div class="popover-content">
					<div id="" class="">
						<span class="btn_left">
							<input type="submit" id="tb" value="同步菜单到微信中" class="btn btn-success">
						</span>
						<span class="btn_right">
							<input type="submit" id="jy" value="禁用自定义菜单" class="btn btn-danger">
						</span>
					</div>
					
				</div>
			</div>
		</div>

		<!-- 编辑区 -->
		<div id="menutable" class="" style="border:1px solid #bababa; width:580px; height:360px;
		position:relative;top:20px;left:10px;border-radius: 6px;
		">
			
		</div>
	</div>
</div>

<!-- ------关键词弹出层------------->
<div id="select_keyword" class="easyui-window" title="关键词列表" 
	data-options="modal:true,closed:true,collapsible:false,draggable:true,
	minimizable:false,maximizable:true,resizable:true" style="width:600px;height:400px;">
	<table id="dg" class="easyui-datagrid" 
                 	data-options="url : '${webroot }/msg/autoresponseDatagrid',
                 	fit:true, pagination:false, pageSize:10, fitColumns:false,
                 	loadMsg:'正在加载...',rownumbers:true,singleSelect:true,striped:true,
                            sortName:'addTime', sortOrder:'desc',idField:'id'">
		<thead>
			<tr>
				<th data-options="field:'id',editor:'text',hidden:true">id</th>
				<th data-options="field:'keyword',width:150,align:'center',editor:'text'">关键词</th>
				<th data-options="field:'msgType',width:80,align:'center',editor:'text'">消息类型</th>
				<th data-options="field:'templateName',width:200,align:'center',editor:'text'">模板名称</th>
				<th data-options="field:'opt',width:50,align:'center',editor:'text',formatter:optFormatter">操作</th>
			</tr>
		</thead>
	</table>
</div>

 </body>
<script>
$(function(){  
	//allMenus
	$.post("${webroot }/menu/mymenu",
			function(data){
				$(".allMenus").html(data);
			}
	);

	//同步
	var sync = $("#tb");
	sync.click(function(){
		$.post('${webroot}/menuController/sync',
        		function(data, status){
					$.messager.show({
						msg : data.msg,
						title : '提示'
					});
        		},
        		"json"
        );
    });
	
});

function optFormatter(value,row,index){
	return '&nbsp;&nbsp;<a href="#" style="color:blue;" onclick="getkeyword(&quot;'+row.id+'&quot;,&quot;'+row.keyword+'&quot;)">确定</a>&nbsp;&nbsp; ';
}
//获取关键词
function getkeyword(id,key){ 
	$('#key_word_id').val(id); 
	$('#key_word').val(key); 
	$('#select_keyword').window('close'); 
}

//显示主菜单内容
function getMainMenu(obj){
	var MenuObj = obj.getElementsByTagName("a");
	
	var menuValue = $.trim(MenuObj[0].innerHTML);//菜单名称
	//$("#menu").val(menuValue);  //暂时不用这种赋值
	//var name=encodeURI(encodeURI(menuValue));//两次使用encodeURI() 

	var menuId = $.trim(MenuObj[1].innerHTML);//菜单id
	var menuState = $.trim(MenuObj[2].innerHTML);//菜单状态
	var menuType = $.trim(MenuObj[3].innerHTML);//菜单类型
	var menuParam1 = $.trim(MenuObj[4].innerHTML);//菜单参数一
	var menuParam2 = $.trim(MenuObj[5].innerHTML);//菜单参数二
	

	var dl = obj.parentNode.getElementsByTagName("dl")[0];
	dl.style.display=(dl.style.display === "none")?"":"none";


	$.post("${webroot }/menu/toMainMenuTable",
			{
				"name" : menuValue,
				"id" : menuId,
				"state" : menuState,
				"type" : menuType,
				"param1" : menuParam1,
				"param2" : menuParam2
			},
			function(data){
				$("#menutable").html(data);
			}
	);
			
}
//显示子菜单内容
function getSubMenu(obj){
	var MenuObj = obj.getElementsByTagName("a");
	var menuValue = $.trim(MenuObj[0].innerHTML);

	var menuId = $.trim(MenuObj[1].innerHTML);
	var menuType = $.trim(MenuObj[2].innerHTML);//菜单类型
	var menuParam1 = $.trim(MenuObj[3].innerHTML);//菜单参数一
	var menuParam2 = $.trim(MenuObj[4].innerHTML);//菜单参数二
	
	console.log(menuValue);

	$.post("${webroot }/menu/tosubMenuTable",
			{
				"name" : menuValue,
				"id" : menuId,
				"type" : menuType,
				"param1" : menuParam1,
				"param2" : menuParam2
			},
			function(data){
				$("#menutable").html(data);
			}
	);
			
}

//更改提示信息
function changeAlertMsg(msg){

	$("#alertMsg").html('<div class="alert alert-info"><button type="button" class="close" data-dismiss="alert"> ×  </button> '
			+ ' <strong>  提示! </strong>  &nbsp;&nbsp; '
			+ msg + ' </div>');
	
}
//加载新增主菜单模块
function newMainMenu(){
	$.post("${webroot }/menu/toMainMenuTable",
			{
				"name" : '',
				"id" : '',
				"state" : 'closed',
				"newOrUpd" : 'new'
			},
			function(data){
				$("#menutable").html(data);
			}
	);
	
}
//加载新增子菜单模块
function newSubMenu(obj){
	var pid = obj.getElementsByTagName("a")[1].innerHTML;
	$.post("${webroot }/menu/tosubMenuTable",
			{
				"name" : '',
				"id" : pid,
				"newOrUpd" : 'new'
			},
			function(data){
				$("#menutable").html(data);
			}
	);
	
}
//新增菜单
function addMenu(parentId){
	var name = $('#menu').val();
	var menuType = '0';
	var keyWord = $('#key_word_id').val();
	var webSite = document.getElementById('web_site_name').innerHTML;
	var url = $('#url').val();
	
	var x = document.getElementById("mySelect");
	if (x.options[0].selected == true) {
	}
	if (x.options[1].selected == true) {
		menuType = '1';
	}
	if (x.options[2].selected == true) {
		menuType = '2';
	}
	if (x.options[3].selected == true) {
		menuType = '3';
		if(!checkURL(url)){
			$.messager.show({
				msg : '您输入的网址没有以http://开头',
				title : '提示'
			});
			return;}//判断
	}

	$.post("${webroot }/menu/add",
			{
				"name" : name,
				"menuType" : menuType,
				"keyWord" : keyWord,
				"webSite" : webSite,
				"url" : url,
				"pid" : parentId
			},
			function(data){
				console.log(data);
				if(data.success){
					$.post("${webroot }/menu/mymenu",
							function(data){
								$(".allMenus").html(data);
							}
					);
				}
				$.messager.show({
					msg : data.msg,
					title : '提示'
				});
			},'JSON');
	
}
//修改
function updMenu(){
	var id = $('#menuid').val();
	var name = $('#menu').val();

	var menuType = '0';
	var keyWord = $('#key_word_id').val();
	var webSite = document.getElementById('web_site_name').innerHTML;
	var url = $('#url').val();
	
	var x = document.getElementById("mySelect");
	if (x.options[0].selected == true) {
	}
	if (x.options[1].selected == true) {
		menuType = '1';
	}
	if (x.options[2].selected == true) {
		menuType = '2';
	}
	if (x.options[3].selected == true) {
		menuType = '3';
		if(!checkURL(url)){
			$.messager.show({
				msg : '您输入的网址没有以http://开头',
				title : '提示'
			});
			return;}//判断
	}

	$.post("${webroot }/menu/update",
			{
				"id" : id,
				"name" : name,
				"menuType" : menuType,
				"keyWord" : keyWord,
				"webSite" : webSite,
				"url" : url
			},
			function(data){
				console.log(data);
				if(data.success){
					$.post("${webroot }/menu/mymenu",
							function(data){
								$(".allMenus").html(data);
							}
					);
				}
				$.messager.show({
					msg : data.msg,
					title : '提示'
				});
			},'JSON');
	
}
//检查url的合法性
function checkURL(url){
	var start = url.indexOf("http://");
	if(start != 0){
	   //表示utl不是以http://开头
	   return false;
	}
	else{return true;}
}
</script>
</html>

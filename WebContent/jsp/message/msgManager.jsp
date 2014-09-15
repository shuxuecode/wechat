<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" type="text/css" href="${webroot }/style/layout/secondMaster.css" />
	<script type="text/javascript" src="${webroot }/jslib/layout/leftmenuClick.js"></script>
	<script type="text/javascript" src="${webroot }/jslib/layout/swithchTag.js"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" style="margin:0px; height:100%;">
  <div data-options="region:'west'" style=" overflow-x:hidden; width:200px; height:100%;position:relative ">
   <!-- menu 开始--------------------------------->
<div id="wrapper" style="height:400px;">
  <div class="left_xz"></div>
  <ul class="leftmenu">
    <li><a href="#">信息操作</a>
      <ul class="sub-menu">
        <li id="tag1"><a href="#" onclick="leftmenu_switchTag('tag1','${webroot }/msg/tomsgShow');" class="selectli">信息</a></li>
        <li id="tag2"><a href="#" onclick="leftmenu_switchTag('tag2','${webroot }/msg/toSmart');">智能回复设置</a></li>
        <li id="tag3"><a href="#" onclick="leftmenu_switchTag('tag3','${webroot }/msg/toautoresponseList');">关键词管理</a></li>
        <li id="tag4"><a href="#" onclick="leftmenu_switchTag('tag4','${webroot }/msg/tomassList');">群发消息管理</a></li>
        <li id="tag5"><a href="#" onclick="leftmenu_switchTag('tag5','${webroot }/msg/togroupList');">分组管理</a></li>
      </ul>
    </li>
    <li><a href="#">消息模板管理</a>
      <ul class="sub-menu">
        <li id="tag8"><a href="#" onclick="leftmenu_switchTag('tag8','${webroot }/msg/totextTemplateList');" class="selectli">文本消息</a></li>
        <li id="tag9"><a href="#" onclick="leftmenu_switchTag('tag9','${webroot }/msg/tonewsTemplateList');">图文消息</a></li>
      </ul>
    </li>
  </ul>
</div>
    <!--menu结束---------------------------------------------------------------------->
  </div>
  <div class="center" data-options="region:'center'" style="overflow: hidden;">
      <iframe id="iFrame1" name="iFrame1" width="100%" height="100%" frameborder="0" scrolling="auto" src="${webroot }/msg/tomsgShow"></iframe>
  </div>
</div>
</body>
<script>

</script>
</html>

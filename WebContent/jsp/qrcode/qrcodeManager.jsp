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
    <li><a href="#">CODE</a>
      <ul class="sub-menu">
        <li id="tag1"><a href="#" onclick="leftmenu_switchTag('tag1','${webroot }/qrcode/toqrcodeList');" class="selectli">二维码</a></li>
        <li id="tag3"><a href="#" onclick="leftmenu_switchTag('tag3','${webroot }/qrcode/touserinfoList');" >关注者信息</a></li>
      </ul>
    </li>
  </ul>
</div>
    <!--menu结束---------------------------------------------------------------------->
  </div>
  <div class="center" data-options="region:'center'">
      <iframe id="iFrame1" name="iFrame1" width="100%" height="100%" frameborder="0" scrolling="auto" src="${webroot }/qrcode/toqrcodeList"></iframe>
  </div>
</div>
</body>
</html>

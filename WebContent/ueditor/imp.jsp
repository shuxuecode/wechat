<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 引入百度编辑器插件 -->
<script type="text/javascript">
      //修正上传控件根路径
      window.UEDITOR_HOME_URL = "${pageContext.request.contextPath}/ueditor/"; 
      //工程根路径，便于上传成功图片地址回传并显示图片
      //window.REAL_PATH = "${pageContext.request.contextPath}/";
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/ueditor/themes/default/css/ueditor.css" rel="stylesheet"/>
<script type="text/javascript" charset="utf-8"  
    src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script> 
     
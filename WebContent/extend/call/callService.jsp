<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拨打客服电话</title>
</head>
<script type="text/javascript">
	var ua = navigator.userAgent.toLowerCase();
	if(ua.indexOf("iphone") > -1){
		//拨打电话iphone
		document.write('<a href="callto:10086" style="font-size: 30px; position: relative; top: 100px; text-decoration: underline;" target="_blank" rel="nofollow">拨打10086客服(仅供测试之用)</a>')
	}
	else{
		//拨打电话android
		document.write('<a href="wtai://wp/mc;10086" style="font-size: 30px; position: relative; top: 100px; text-decoration: underline;" target="_blank" rel="nofollow">拨打10086客服(仅供测试之用)</a>')
	}
</script>
<body style="text-align:center;">

</body>
</html>
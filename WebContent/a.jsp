<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
var browser=navigator.appName
var b_version=navigator.appVersion
var version=parseFloat(b_version)
document.write("Browser name: "+ browser)
document.write("<br />")
document.write("Browser version: "+ version)
document.write("<br />")
document.write("<br />")
document.write("Browser userAgent : "+ navigator.userAgent)
document.write("<br />")
document.write("<br />返回浏览器的代码名。<br />")
document.write("Browser appCodeName : "+ navigator.appCodeName)

document.write("<br />返回浏览器的次级版本。<br />")
document.write("Browser appMinorVersion : "+ navigator.appMinorVersion)

document.write("<br />返回当前浏览器的语言。<br />")
document.write("Browser browserLanguage : "+ navigator.browserLanguage)

document.write("<br />返回指明浏览器中是否启用 cookie 的布尔值。<br />")
document.write("Browser cookieEnabled : "+ navigator.cookieEnabled)

document.write("<br />返回浏览器系统的 CPU 等级<br />")
document.write("Browser cpuClass : "+ navigator.cpuClass)

document.write("<br />返回指明系统是否处于脱机模式的布尔值<br />")
document.write("Browser onLine : "+ navigator.onLine)

document.write("<br />返回运行浏览器的操作系统平台<br />")
document.write("Browser platform : "+ navigator.platform)

document.write("<br />返回 OS 使用的默认语言<br />")
document.write("Browser systemLanguage : "+ navigator.systemLanguage)

document.write("<br />返回 OS 的自然语言设置<br />")
document.write("Browser userLanguage : "+ navigator.userLanguage)


//console.info(navigator.platform);
//console.info(navigator.userAgent);

//funtion myBrower(){
//	var userAgent = navigator.userAgent;
//	if(userAgent.indexOf("Opera") > -1){return "Opera";}
//	if(userAgent.indexOf("Firefox") > -1){return "Firefox";}
//	if(userAgent.indexOf("Chrome") > -1){return "Chrome";}
//	if(userAgent.indexOf("Safari") > -1){return "Safari";}
//
//}
 
</script>
</body>
</html>
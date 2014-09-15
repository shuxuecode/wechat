<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%> 	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信平台 —— ——注册</title>
<link rel="icon" href="${pageContext.request.contextPath }/images/main/ico_32.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${pageContext.request.contextPath }/images/main/ico_32.ico" type="image/x-icon" />

<link rel="stylesheet" href="${pageContext.request.contextPath }/style/main/register.css" type="text/css" />

<script src="${webroot }/jslib/backtop.js" charset="utf-8"></script>

</head>
<body>
<div class="main">
	
	<div class="header">
		<div class="logo">LOGO</div>
		<div class="login-link">
			<span>我已注册，现在就</span>
			<button class="login-btn" id="login_btn">登录</button>
		</div>
	</div>
	
	<div id="register" class="register">
	<form action="" id="" method="post">
	<table id="tab" border="0" cellspacing="0" cellpadding="0" class="">
		<tbody>
			<tr>
				<th class="titip"><span class="must">*</span>用户名&nbsp;:&nbsp;</th>
				<td>
					<input class="" id="user_name" maxlength="20" name=""
					placeholder="全站唯一" size="30" type="text" onblur="checkusername()">
					<p class="remind">3-20个字符，允许中文、英文字母、数字和符号</p>
				</td>
			</tr>
	
			<tr>
				<th class="titip"><span class="must">*</span>邮箱&nbsp;:&nbsp;</th>
				<td>
					<input class="" id="user_email" name="" size="30"
					type="text">
					<ul>
						<li class="remind">请填写常用邮箱，以便我们给您发送安全服务邮件</li>
					</ul>
				</td>
			</tr>
			<tr>
				<th class="titip"><span class="must">*</span>密码&nbsp;:&nbsp;</th>
				<td>
					<input class="" id="user_password" name=""
					placeholder="6-20个字符" size="30" type="password">
					<p class="remind">长度为6~14个字符,支持数字、大小写字母和标点符号,不允许有空格</p>
				</td>
			</tr>
			<tr>
				<th><span class="must">*</span>确认密码&nbsp;:&nbsp;</th>
				<td>
					<input class="required" id="user_confirm" size="30" type="password"
					onBlur="validatePwd();">
				</td>
			</tr>
	
			<tr>
				<td colspan="2" class="line">&nbsp;</td>
			</tr>
	
			<tr>
				<th><span class="must">*</span>验证码&nbsp;:&nbsp;</th>
				<td>
					<input class="required" id="validateCode" size="30" type="text"
					maxlength="4"> 
					<span> 
						<img id="verify" title="验证码图片" alt="验证码图片" class="pass-verifyCode"
						src="${webroot }/validatecode"  onclick="reloadcode()" style="cursor: pointer; vertical-align: middle;">
					</span> 
					<a onclick="reloadcode()" href="javascript:void(0);" class="change">换一张</a>
				</td>
			</tr>
	
			<tr>
				<td colspan="2" class="line">&nbsp;</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input name="checkbox" type="checkbox" id="auto"
					checked="checked"> <label for="auto" class="auto">我已经阅读并同意&nbsp;</label>
				<a href="#" target="_blank" class="clause">注册条款</a></td>
			</tr>
		</tbody>
	</table>
	
	<div class="reg-sms" style="">
		<div class="tip"></div>
		<div id="warn" style="display:none;">
			<div class="tishi">提示</div>
			<div id="warn_msg" class="tsmsg"></div>
		</div>
	</div>
	
	<div class="">
		<input type="button" name="button" id="button"
		value="注　册" class="submit_btn">
	</div>
	</form>
	</div>
	
	
	<div class="footer">
		<div class="">
			<div class="copy-box">2014&nbsp;©Org</div>
		</div>
	</div>

</div>
</body>
<script>
$(function () {

	$('#tab input').each(function(){
		$(this).val('');
	});
	
	var login_btn = $("#login_btn");
	login_btn.click(function(){
		location.replace("${pageContext.request.contextPath}/wechat");
    });

	// 注册
	$('#button').click(function() {
	    if (check() == true) {
	        var name = $.trim($('#user_name').val());
	        var email = $.trim($('#user_email').val());
	        var pwd = $.trim($('#user_password').val());
	        var code = $.trim($('#validateCode').val());
	        var param = {
	            name: name,
	            pwd: pwd,
	            email: email,
	            code: code
	        };
	        $.post('${pageContext.request.contextPath}/signin', param,
	        function(data) {
	            console.log(data);
	            if (!data.success) {
	            	warn(true,data.msg);
	                reloadcode();
	                return;
	            } else {
	            	regsuccess();
	            }
	        },
	        "json");
	    }

	});


	//验证
    function check() {
        if (passSpace($('#user_name').val()) == '') {
            warn(true,'用户名不能为空');
            $('#user_name').focus();
            return false;
        }

		if (passSpace($('#user_email').val()) == '') {
            warn(true,'邮箱不能为空');
            $('#user_email').focus();
            return false;
        }
        
        if (passSpace($('#user_password').val()) == '') {
            warn(true,'密码不能为空');
            $('#user_password').focus();
            return false;
        }

        if (passSpace($('#user_confirm').val()) == '') {
            warn(true,'请输入确认密码');
            $('#user_confirm').focus();
            return false;
        }
        
        if (passSpace($('#validateCode').val()) == '') {
            warn(true,'验证码不能为空');
            $('#validateCode').focus();
            return false;
        }
        warn(false,'');
        return true;
    }


    

});    
function reloadcode(){
    document.getElementById('verify').setAttribute('src','${pageContext.request.contextPath}/validatecode?z='+Math.random());
}
function checkusername(){
	if (passSpace($('#user_name').val()) == '') {
        warn(true,'用户名不能为空');
        $('#user_name').focus();
        return;
    }
    $.post('${pageContext.request.contextPath}/checkname', 
    	    {'username' : $('#user_name').val()},
	        function(data) {
	            console.log(data);
	            if (!data.success) {
	            	warn(true,'用户名已存在,请重新输入!');
	            	 $('#user_name').focus();
	                return;
	            }
	            else{warn(false,'');}
	        },
	 "json");
}
/**
 * 去除空格
 * 参数 val 需去除空格的字符串
 * return 去除空格后的字符串
 * */
function passSpace(val) {
    var reVal = val.replace(/[ ]/g, "");
    return reVal;
}
//检验密码一致
function validatePwd() {
    if(passSpace($('#user_password').val()) === passSpace($('#user_confirm').val())){
    	warn(false,'');
    }
    else{warn(true,'两次密码不一致，请重新输入');}
}

var warnDiv = document.getElementById('warn');
var warnMsg = document.getElementById('warn_msg');
function warn(show,msg){
    if(show){warnDiv.style.display = 'block';warnMsg.innerHTML = msg;}
    else{warnDiv.style.display = 'none';}
}

function regsuccess(){
	document.getElementById('register').innerHTML = '<div id=""style="display:block;width:840px;height:508px;"><div style="font-size: 30px; color:#666; font-weight: bold; margin: 200px 0px 0px 80px;"><div style="float: left; height: 64px; width: 64px; background: url(&quot;http://bcs.duapp.com/wechatimages/image/icon_large.gif&quot;) no-repeat scroll -64px 0px transparent;position: relative;bottom: 20px;right: 15px;"></div>注册成功！<a href="${pageContext.request.contextPath}/wechat"style="font-size:24px;">点击登录</a></div></div>';
}
</script>
</html>
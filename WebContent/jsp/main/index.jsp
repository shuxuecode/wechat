<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信平台</title>
<link rel="icon" href="${webroot }/images/main/ico_32.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${webroot }/images/main/ico_32.ico" type="image/x-icon" />

<link rel="stylesheet" href="${webroot }/style/main/style.css" type="text/css" />
<script type="text/javascript" src="${webroot }/jslib/main/jquery.themexSlider.js"></script>
<script type="text/javascript" src="${webroot }/jslib/main/jquery.hoverIntent.min.js"></script>
<script type="text/javascript" src="${webroot }/jslib/main/jquery.custom.js"></script>

<script type="text/javascript" src="http://bcs.duapp.com/extend/js/backtop.js" charset="utf-8"></script>
</head>
<body>
<div class="site" >
	<!-- top -->
	<div id="header" class="header">
		<div id="" class="logo" style="float:left;">
			<marquee scrollAmount="3" behavior="alternate" width="215" onmouseover="stop()" onmouseout="start()" style="margin:0px 0 0 0px;">
				<b style="font-size:30px;color:#ccc"> <span>L</span><span>O</span><span>G</span><span>O</span></b>
			</marquee>
		</div>
		<div id="" class="login-area" style="float:right;margin: 15px 80px 0px 0px;">
			<a href="${webroot }/wechat"><span class="">登录</span></a>
			&nbsp;&nbsp;
			<a href="${webroot }/register"><span class="">注册</span></a>
		</div>
	</div>
	<!-- nav -->
	<div id="main1" style="">
    
        <div class="parallax-slider themex-slider">
            <div class="substrate">
                <img src="${webroot }/images/main/site_bg.jpg" alt="" />
            </div>
            <ul>
                <li>
                    <div style="margin:0 auto;padding:0 30px;">
                        <div style="">
                            <img src="${webroot }/images/main/slide_1.png" class="theH" />
							<span class="theH t">
								<div style="border:0px solid blue; margin-top: 100px;">
									<div style="height:50px;font-size:25px;">
										打造一流微信平台
									</div>
										balabalabala
									<div style="height:50px;font-size:35px;">
										<a href="${webroot }/register">马上注册</a>
									</div>
								</div>
							</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div style="margin:0 auto;padding:0 30px;">
                        <div>
                            <img src="${webroot }/images/main/slide_2.png" class="theH" />
							<span class="theH t">
								<div style="color: #ffffff;margin-top: 100px;font-size:25px;">
								介绍
								</div>
							</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div style="margin:0 auto;padding:0 30px;">
                        <div>
                            <img src="${webroot }/images/main/slide_3.png" class="theH" />
							<span class="theH t">
								<div style="margin-top:100px;font-size:25px;" >
								解释
								</div>
							</span>
                        </div>
                    </div>
                </li>
            </ul>
            <div class="arrow arrow-left">
            </div>
            <div class="arrow arrow-right">
            </div>
            <!-- 控制动画 -->
            <input type="hidden" class="slider-pause" value="0" />
            <input type="hidden" class="slider-speed" value="1000" />
            <input type="hidden" class="slider-effect" value="slide" />
        </div>
    
	</div>
	<br><br>

	<div id="" class="">
		<img src="${webroot }/images/main/gg.png">
	</div>

<div id="" style="height:300px;">
	<div id="" style="font-size:20px;margin-left:50px;">账号分类</div>
	<br><br>
	<div id="" class="">
		<div id="" class="mok">
			<div id="" class="pic">
				<img src="${webroot }/images/main/fuwu.png">
			</div>
			<div id="" class="art">
				
			</div>
		</div>
		<div id="" class="mok">
			<div id="" class="pic">
				<img src="${webroot }/images/main/dingyue.png">
			</div>
			<div id="" class="art">
				
			</div>
		</div>
		
	</div>
</div>


	<div id="" class="">
		<img src="${webroot }/images/main/img2.png">
	</div>
	<br><br>
	<div id="" class="">
		<img src="${webroot }/images/main/img1.png">
	</div>
	<br>
</div>
<!-- 底部 -->

<div id="footer">
    <span>
        <a target="_blank" href="#">
            关于
        </a>
        &nbsp;|&nbsp;
        <a target="_blank" href="#">
            客服中心
        </a>
        &nbsp;|&nbsp;
        <a href="#"
        target="_blank">
            在线客服
        </a>
        &nbsp;|&nbsp;
        <a target="_blank" href="#">
            联系邮箱
        </a>
    </span>
    <span>
        Copyright&nbsp;© 2014 org. All Rights Reserved.
    </span>
</div>
</body>
</html>

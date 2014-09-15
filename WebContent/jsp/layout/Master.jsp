<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  style="margin:0px; height:100%">
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>微信平台 By Highness</title>
    <link rel="icon" href="${webroot }/images/main/ico_32.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${webroot }/images/main/ico_32.ico" type="image/x-icon" />
    
    <link rel="stylesheet" type="text/css" href="${webroot }/style/layout/Master.css" />
    <script type="text/javascript" src="${webroot }/jslib/layout/Master.js"></script>
    <script type="text/javascript">
    $(function() {
		$('#AllMaster').layout({
			fit : true
		});
		//刷新显示时间
		window.setInterval(function(){
			document.getElementById('current_time').innerHTML = new Date().toLocaleString();
		},1000);
    });
    </script>
</head>  
<body> 

<jsp:include page="/jsp/login/login.jsp"></jsp:include>

    <div id="AllMaster">  
        <div class="north" data-options="region:'north'">
        	<div class="north_li" data-options="region:'north'">
            	<div class="logo">
                	<!--  <img style="margin-top:20px;" src="${pageContext.request.contextPath }/interface/Image/logo1.png" />-->
                	<marquee scrollAmount="3" behavior="alternate" width="415" onmouseover="stop()" onmouseout="start()" style="margin:20px 0 0 20px;">
						<b style="font-size:30px;color:#ccc"> <span>L</span><span>O</span><span>G</span><span>O</span></b>
					</marquee>
                </div>
                
                <div class="headright">
                    <table class="tableright">
                    	<tr>
                          <td>
                            	<img src="${webroot }/images/layout/icon2.png" />
                          </td>
                          <td onMouseover="$('.userinfo')[0].style.display = 'block';"
                          	  onMouseout="$('.userinfo')[0].style.display = 'none';">
                            	<a href="#">个人信息</a>
                            	<div class="userinfo" style="display:none;">
								  <ul>
								    <li><a href="#">修改密码</a></li>
								    <li><a href="#">切换用户</a></li>
								  </ul>
								</div>
                          </td>
                          <td>
                            	<img src="${webroot }/images/layout/icon1.png" />
                          </td>
                          <td>
                            	<a href="#">帮助</a>
                          </td>
                          <td>
                            	<img src="${webroot }/images/layout/icon3.png" />
                          </td>
                          <td>
                            	<a href="javascript:void(0);" id="logout">退出</a>
                            	<input type="hidden" id="logout1" value="${webroot }/logout"/>
                            	<input type="hidden" id="logout2" value="${webroot }/wechat"/>
                          </td>
                        </tr>
                    </table>
                </div>
                <!-- 显示当前账户名 -->
                <div id="account_name"></div>
                <!-- 显示当前时间 -->
                <div id="current_time"></div>
            </div>
            <!--menu开始-------------------------------------------------------------------->
            <div id="container">
                <div id="title">
                            <ul>
                              <li id="tag1"><a href="#" onclick="switchTag('tag1','${webroot }/sys/toSystemManager');this.blur();" class="selectli"><span class="selectspan"><img src="${webroot }/images/layout/xin.png" align="absmiddle" />&nbsp;1首页</span></a></li>
                              <li id="tag2"><a href="#" onclick="switchTag('tag2','${webroot }/user/toUserManager');this.blur();"><span><img src="${webroot }/images/layout/xue.png" align="absmiddle" />&nbsp;2用户管理</span></a></li>
                              <li id="tag3"><a href="#" onclick="switchTag('tag3','${webroot }/msg/toMsgManager');this.blur();"><span><img src="${webroot }/images/layout/dang.png" align="absmiddle" />&nbsp;3信息申请</span></a></li>
                              <li id="tag6"><a href="#" onclick="switchTag('tag6','${webroot }/menu/toMenuManager');this.blur();"><span><img src="${webroot }/images/layout/ge.png" align="absmiddle" />&nbsp;4菜单管理</span></a></li>
                              <li id="tag8"><a href="#" onclick="switchTag('tag8','${webroot }/account/toAccountManager');this.blur();"><span><img src="${webroot }/images/layout/Ren.png" align="absmiddle" />&nbsp;5账户管理</span></a></li>
                              <li id="tag4"><a href="#" onclick="switchTag('tag4','${webroot }/qrcode/toQrcodeManager');this.blur();"><span><img src="${webroot }/images/layout/Ren.png" align="absmiddle" />&nbsp;6二维码管理</span></a></li>
                            </ul>
                </div> 
            </div>
        	<!--menu结束---------------------------------------------------------------------->
            
        </div>  
        
        <div class="center" data-options="region:'center'" style="width:100%; height:100%;overflow:hidden">
            <div id="mains" class="mains">
            	<iframe id="iFrame" name="iFrame" width="100%" height="100%" frameborder="0" src=""></iframe>
            </div>
        </div>  
        <div class="south" data-options="region:'south'">
        	<div class="bottom_font">CopyRight © 2014-2014 石家庄火云 , All Rights Reserved</div>
        </div>
    </div>
</body>  
</html>

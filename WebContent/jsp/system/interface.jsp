<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
			.main {
				padding: 50px 130px;
				font-family: Arial, "宋体";
				color: #666;
			}
			.header{
				border-bottom: 1px solid #666;
			}
			.center{
				border: 1px solid #CCCCCC;
				padding: 30px;
				margin-top: 20px;
				border-radius: 3px;
			}
			.center .title{
				display: inline-block;
				width: 200px;
				height: 38px;
			}
			.warn{
			    font-size: 18px;
			    height: 44px;
			    line-height: 40px;
			    margin: 30px 140px;
			    text-align: center;
			    width: 220px;
			    border-radius: 3px;
			    color: white;
			}
			.noconfig{
				background-color: #F24D4D;
			}
			.yesconfig{
				background-color: #35AA47;
			}
		</style>
</head>
<body>
<div class="main">
			<div class="header">
				<p>接口信息</p>
			</div>
			<div class="center">
				<div>
					<span class="title">URL</span>
					<span id="">${url }</span>
				</div>
				<div>
					<span class="title">Token</span>
					<span id="">${token }</span>
				</div>
			</div>
			<div class="">
				<%
					String binding = (String)request.getAttribute("binding");
					if("yes".equals(binding)){
				%>
					<div id="" class="warn yesconfig">
					  已绑定微信
					</div>
					
				<%} else{%>
				
					<div id="" class="warn noconfig">
					  未绑定微信
					</div>
				<%} %>
			</div>
		</div>
</body>
</html>
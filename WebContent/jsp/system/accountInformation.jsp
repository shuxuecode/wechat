<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="${webroot }/style/system/information_style.css" />
</head>
<body>
	<div id="" class="main">
		<div id="" class="header">

		</div>
		<div id="" class="center">

		</div>

	</div>
</body>
<script type="text/javascript">
$(function(){  
	//加载信息
	getInformation();
	
});
//修改
function update(){
	$.post('${webroot }/account/updateInformation',
    		function(data){
				$(".center").html(data);
    		}
    );
}
//保存修改
function save(){
	var fd = document.getElementById("Form");
    var formData = new FormData(fd);
    $.ajax({
        url: '${webroot }/account/saveInformation',
        type: 'POST',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function(result){
    				console.log(result);
    				if(result.success){getInformation();}
    				$.messager.show({
						msg : result.msg,
						title : '提示'
					});
    			},
        dataType:'json'
    });

	
	
}
//获取账户信息
function getInformation(){
	$.post('${webroot }/account/getInformation',
    		function(data){ $(".center").html(data); }
    );
}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div style="display:none;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">Open</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">Close</a>
</div>

		<div id="w" class="easyui-window" title="" data-options="
		collapsible:false,minimizable:false,
		maximizable:false,closable:false,draggable:false,resizable:false,modal:true, maximized:true" style="width:100%;height:100%;padding:0px;">
			<div  class="" style="text-align:center;padding-top:200px;">
				<form id="ff" method="post">
					<table border="0" cellspacing="0" cellpadding="0" style="margin:0 auto;">
						<tr>
							<th  align="right">登录名</th>
							<td style="padding-right:20px; padding-top:7px;">
								<input name="name" type="text" placeholder="请输入登录名" 
								class="easyui-validatebox" data-options="required:true" value="zhao" autofocus />
							</td>
						</tr>
						<tr>
							<th  align="right">密码</th>
							<td style="padding-right:20px; padding-top:7px;">
								<input name="pwd" type="password" placeholder="请输入密码" 
								class="easyui-validatebox" data-options="required:true" value="123">
							</td>
						</tr>
					</table>
				</form>
			
				<button type="button" onclick="loginFun()" style="margin: 80px 0 0 145px;">登录</button>
				
			</div>
		</div>
<script>
$(function(){


	$('#w input').keyup(function(event) {
		if (event.keyCode == '13') {
			loginFun();
		}
	});


	
});



function loginFun(){
	
    var formData = new FormData(document.getElementById("ff"));
	
    $.ajax({
        url: '${pageContext.request.contextPath}/login', 
        type: 'POST',
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        success: function(result){
    			console.info(result === '');
    			if(result !== ''){
        			$('#w').window('close');
        			
        			$('#account_name').text('欢迎! ' + result);
        			//
        			document.getElementById("iFrame").src = '${pageContext.request.contextPath }/sys/toSystemManager';
        			$.messager.show({
        				msg : '登录成功',
        				title : '提示'
        			});
        		}
    			else{alert('登录失败');}
    	},
        //dataType:'json'
        dataType:'text'
    });
    
}




</script>
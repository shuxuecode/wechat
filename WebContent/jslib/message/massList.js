//
$(function () {
	
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		$('#add').window('open');
		$('#add').window('center');
    });
	
	//新增群发消息
	var add_mass_url = $('#add_mass_url').val();
	var toSave_mass = $("#toSave_mass");
	//提交新增表单
	toSave_mass.click(function(){
		var massName = $('#massName').val();
		$.post(add_mass_url,
				{'massName' : massName},
				function(result){
					console.log(result);
					$('#add').window('close');
					if(result.success){
						$("#dg").datagrid('reload',{});
					}
					$.messager.show({
						msg : result.msg,
						title : '提示'
					});
				},
				'json'
		);
    });
	
	
	
	
	//批量删除
	var del_url = $('#del_mass_url').val();
	var del_btn = $("#del_btn");
	//
	del_btn.click(function(){
		var rows = $("#dg").datagrid('getSelections');
		if (rows.length <= 0) {
			$.messager.show({
				msg : '请选择要删除的信息！',
				title : '提示'
			});
		}
		else {
			var ids = [];
			$.messager.confirm(
					'请确认',
					'您确定要删除当前所选的群发消息吗？',
					function(b){
						if(b){
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$.post(
									del_url,
									{
										'ids' : ids.join(',')
									},
									function(returnData, status) {
										// 重新加载数据
										$("#dg").datagrid('load');
										$("#dg").datagrid('unselectAll');
										$.messager.show({
											msg : result.msg,
											title : '提示'
										});

									});
						}});
		}
		
    });
	
	//添加图文
	var add_news_url = $('#add_news_url').val();
	var toSave_news = $("#toSave_news");
	//提交新增表单
	toSave_news.click(function(){
		$('#fade').show();
		var fd = document.getElementById("Form_news");
        var formData = new FormData(fd);
        formData.append('showcover' , document.getElementById('showcover').checked);
        $.ajax({
            url: add_news_url,
            type: 'POST',
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function(result){
        				$('#fade').hide();
        				console.log(result);
        				if(result.success){
        					$('#add_news').window('close');
        			        $("#dg").datagrid('load',{});
        				}
    					$.messager.show({
    						msg : result.msg,
    						title : '提示'
    					});
        			},
            dataType:'json'
        });
		
	});
	
	
	//分组群发弹出层
	var group_send_btn = $("#group_send");
	group_send_btn.click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length != 1 && rows.length != 0) {
			$.messager.alert('提示', '请选择一个群发消息进行群发操作！', 'info');
			return;
		} else if (rows.length == 0) {
			$.messager.alert('提示', '请选择一个群发消息！', 'info');
			return;
		}
		if (rows.length == 1) {
			var row = $("#dg").datagrid('getSelected');
			$('#group_send_mass_id').val(row.id);
			$('#send_group_div').window('open');
			$('#send_group_div').window('center');
		}
		
    });
	
	//openid群发弹出层
	var openid_send_btn = $("#openid_send");
	openid_send_btn.click(function(){
		var rows = $('#dg').datagrid('getSelections');
		if (rows.length != 1 && rows.length != 0) {
			$.messager.alert('提示', '请选择一个群发消息进行群发操作！', 'info');
			return;
		} else if (rows.length == 0) {
			$.messager.alert('提示', '请选择一个群发消息！', 'info');
			return;
		}
		if (rows.length == 1) {
			var row = $("#dg").datagrid('getSelected');
			$('#openid_send_mass_id').val(row.id);
			$('#send_openid_div').window('open');
			$('#send_openid_div').window('center');
		}
		
    });
	
	//openid群发
	var send_openid_url = $('#send_openid_url').val();
	var send_openid_btn = $("#send_openid_btn");
	send_openid_btn.click(function(){
		var rows = $("#dg_openid").datagrid('getSelections');
		if (rows.length <= 0) {
			$.messager.show({
				msg : '请选择要群发的用户！',
				title : '提示'
			});
		}
		else {
			var ids = [];
			$.messager.confirm(
					'请确认',
					'您确定要群发到当前所选的用户吗？',
					function(b){
						if(b){
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$.post(
									send_openid_url,
									{
										'ids' : ids.join(','),
										"massid" : $('#openid_send_mass_id').val() 
									},
									function(returnData, status) {
										$('#send_openid_div').window('close');
										$.messager.show({
											msg : returnData,
											title : '提示'
										});

									});
						}});
		}
		
    });
	
	
	
});







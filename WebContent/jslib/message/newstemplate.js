
$(function () {
	
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		$('#add').window('open');
		$('#add').window('center');
    });
	
	//
//	var look_btn = $("#look_btn");
//	look_btn.click(function(){
//		$('#see').window('open');
//    });
	
	
	
	
	//新增图文模板
	var add_temp_url = $('#add_temp_url').val();
	var toSave_temp = $("#toSave_temp");
	//提交新增表单
	toSave_temp.click(function(){
		console.info('daolema ');
		var tempName = $('#tempName').val();
		$.post(add_temp_url,
				{'tempName' : tempName},
				function(result){
					console.log(result);
				},
				'json'
		);
        
        $('#add').window('close');
        $("#dg").datagrid('reload',{});
        $.messager.show({
			msg : '新增成功',
			title : '提示'
		});
    });
	
	
	//添加图文
	var add_news_url = $('#add_news_url').val();
	var toSave_news = $("#toSave_news");
	//提交新增表单
	toSave_news.click(function(){
		$('#fade').show();
		var fd = document.getElementById("Form_news");
        var formData = new FormData(fd);
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
        			        $.messager.show({
        						msg : result.msg,
        						title : '提示'
        					});
        				}
        				else{
        					$.messager.show({
        						msg : result.msg,
        						title : '提示'
        					});
        				}
        			},
            dataType:'json'
        });
		
	});
	
	
});










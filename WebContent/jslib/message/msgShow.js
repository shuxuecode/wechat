
$(function () {
	
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		$('#add').window('open');
		$('#add').window('center');
    });
	
	//
	var allTask_btn = $("#allTask_btn");
	allTask_btn.click(function(){
		$("#dg").datagrid('load',{status:3});
		$("#dg").datagrid('unselectAll');
    });
	
	
	
	
	//
	var add_url = $('#add_url').val();
	
	
	var toSave = $("#toSave");
	//提交新增表单
	toSave.click(function(){
        console.log("提交!");
        var fd = document.getElementById("Form1");
        var formData = new FormData(fd);
        $.ajax({
            url: add_url,
            type: 'POST',
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function(result){
        				console.log(result);
        			},
            
            dataType:'json'
        });
        
        $('#add').window('close');
        $("#dg").datagrid('load',{status:0});
        $.messager.show({
			msg : '新增成功',
			title : '提示'
		});
    });
	
	//交由上级
	var submit_url = $('#submit_url').val();
	var submit_btn = $("#submit_btn");
	//提交新增表单
	submit_btn.click(function(){
        console.log("提交!");
        
        var rows = $('#dg').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择一条信息！', 'info');
			return;
		}
		if (rows.length == 1) {
			var row = $("#dg").datagrid('getSelected');
			//
			var id = row.id;
			console.info(id);
			$.post(submit_url,
	        		{
	        			'id' : id
	        		},
	        		function(data, status){
	        			console.info(data);
	        			// 重新加载数据
						$("#dg").datagrid('reload');
						$("#dg").datagrid('unselectAll');
						$.messager.show({
							msg : data,
							title : '提示'
						});
	        		},
	        		"text"
	        );
		}
        
        
    });
	
	
	//驳回
	var refuse_url = $('#refuse_url').val();
	var refuse_btn = $("#refuse_btn");
	//提交新增表单
	refuse_btn.click(function(){
        console.log("驳回");
        
        var rows = $('#dg').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert('提示', '请选择一条信息！', 'info');
			return;
		}
		if (rows.length == 1) {
			var row = $("#dg").datagrid('getSelected');
			//
			var id = row.id;
			console.info(id);
			$.post(refuse_url,
	        		{
	        			'id' : id
	        		},
	        		function(data, status){
	        			console.info(data);
	        			// 重新加载数据
						$("#dg").datagrid('reload');
						$("#dg").datagrid('unselectAll');
						$.messager.show({
							msg : data,
							title : '提示'
						});
	        		},
	        		"text"
	        );
		}
        
    });
	
});









$(function () {
	var getTemplate_url = $('#getTemplate_url').val();
	
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		emptyAll('#add_table');
		$('#add').window('open');
		$('#add').window('center');
    });
	
	//
	$('#msgType').combobox({   
	    valueField:'id',   
	    textField:'text',
	    data:[{id:'text',text:'文本消息'},{id:'news',text:'图文消息'}],
		panelHeight:'auto'
	}); 
	//后期可以添加图片回复等等
	//	data:[{id:'text',text:'文本消息'},{id:'news',text:'图文消息'},{id:'img',text:'img'}],
	
	$('#msgType').combobox({
		onSelect : function(rec) {
			var url = getTemplate_url + '?msgtype=' + rec.id;
			$('#template').combobox('clear');
			$('#template').combobox('reload', url);
		}
	});
	
	
	
	//
	var add_url = $('#add_url').val();
	
	
	
	var toSave = $("#toSave");
	//提交新增表单
	toSave.click(function(){
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
        				if(result.success){
        					$('#add').window('close');
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
	
	
	//删除
	var del_url = $('#del_url').val();
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
					'您确定要删除当前所选的信息？',
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
										$("#dg").datagrid('reload');
										$("#dg").datagrid('unselectAll');
										$.messager.show({
											msg : returnData.msg,
											title : '提示'
										});

									});
						}});
		}
		
    });
	
	
	
	
});


//点击新增数据时清空弹出层残留的数据
function emptyAll(tableId){
	//清空所有input元素中的数据
	$(tableId+' input[type=text]').each(function(){
		$(this).val('');
	});
	//清空所有textarea元素中的数据
	$(tableId+' textarea').each(function(){
		$(this).val('');
	});
}





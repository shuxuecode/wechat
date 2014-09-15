
$(function () {
	
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		emptyAll('#add_table');
		$('#add').window('open');
		$('#add').window('center');
    });
	
	//
	var add_url = $('#add_url').val();
	var toSave = $("#toSave");
	//提交新增表单
	toSave.click(function(){
		$('#fade').show();
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
        				$('#fade').hide();
        				if(result.success){
        					$('#add').window('close');
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





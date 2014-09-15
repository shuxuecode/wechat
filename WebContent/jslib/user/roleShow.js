
$(function () {
	//新增
	var add_btn = $("#add_btn");
	add_btn.click(function(){
		//清空所有input元素中的数据
		$('#add_table input[type=text]').each(function(){
			$(this).val('');
		});
		$('#add').window('open');
		$('#add').window('center');
    });
	//
	var add_url = $('#add_url').val();
	//保存
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
        				if(result.success){
        					$('#add').window('close');
        					$("#dg").datagrid('reload');
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








<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/inc.jsp"%>  

<link href="${webroot }/jslib/bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<script src="${webroot }/jslib/bootstrap-3.2.0-dist/js/bootstrap.min.js" type="text/javascript"></script>

<div style="border:0px solid;">
<form id="update_news" method="post" enctype="multipart/form-data">
 <table style="width: 600px;margin-left: 90px;" border="0">
 	<input name="id" type="hidden" value="${id }"/>
	<tr>
		<td align="left">
			<label class="">
				标题:
			</label>
			<br>
			<input id="title" name="title" type="text" placeholder="填写标题" style="width: 300px" 
				value="${title }"/>
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<label class="" >
				作者<span style="font-size:13px; color:#8d8d8d;">(选填)</span>
			</label>
			<br>
			<input id="author" name="author" type="text" style="width: 300px" value="${author }"/>
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<label class="">
				封面
				<span style="font-size:13px; color:#8d8d8d;">
				(大图片建议尺寸 : 900像素*500像素)(小图片建议尺寸 : 200像素*200像素)
				</span>
			</label>
			<br>
			<span class="btn btn-primary" style="float:left;margin:78px 0 0 30px;">
				<label for="file">浏览</label>
				<br>
				<input type='file' name="coverimg" id="file" onchange="readURL(this,'img_prev');" style="display:none;" accept="image/jpeg"/>  
			</span>
			<img id="img_prev" src="${coverimg }" width="285px" height="160px" style="float:left; margin:15px 0 0 170px;"/>
				
				<input id="imagePath" name="" type="hidden" nullmsg="请添加图片">
		</td>
	</tr>
	
	<tr>
		<td align="left">
			<br>
			<label class="" >
				摘要<span style="font-size:13px; color:#8d8d8d;">(选填)</span>
			</label>
			<br>
			<textarea name="description" id="description" cols="60" rows="2" style="resize:none;">${description }</textarea>
		</td>
	</tr>

	<tr>
		<td align="left"><br>
			<label class="">
				正文
			</label>
			<br>
			<textarea name="content" id="content" cols="60" rows="15" style="resize:none;">${ content}</textarea>
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<div id="link1" style="display:none;">
				<a href="javascript:void(0);" onclick="showhide('link2','link1');">添加原文链接</a>
			</div>
			<div id="link2" style="display:block;">
				<label class="">
					原文链接
				</label>
				<br>
				<input id="url" name="url" type="text" placeholder="填写链接地址" style="width: 440px"
						value="${url }"/>
				&nbsp;&nbsp;
				<a href="javascript:void(0);" onclick="showhide('link1','link2');$('#url').val('');">
					<span style="font-size:12px; color:#888;">取消添加</span>
				</a>
			</div>
			
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<label class="">
				顺序&nbsp;&nbsp;&nbsp;
			</label>
			<input id="sort" name="sort" type="number" min="1" placeholder="选择显示顺序" style="width:120px;"
					value="${sort }"/>
		</td>
	</tr>

</table>
</form>

</div>
<div style="margin: 20px; text-align: right;">
	<button class="btn btn-success" id=""
			onclick="updateNews();">保存</button>
	<button class="btn btn-danger" style="margin: 0 100px 0 30px;"
	 		onclick="$('#updateDiv')[0].style.display = 'none'">取消</button>
</div>
<!-- $.('#objld', parent.document);  -->

<script type="text/javascript">
//HTML5
function readURL(input,img) {  
	if(window.FileReader) {  
		if (input.files && input.files[0]) {
			var imgName = input.files[0].name;
			var idx = imgName.lastIndexOf(".");
			if (idx != -1){   
				var imgType = imgName.substr(idx+1).toLowerCase();
				if (imgType != 'jpg'){  
					$.messager.alert('提示', '请选择jpg类型的图片!', 'info'); 
					return;  
				}   
			} else {  
			 	$.messager.alert('提示', '请选择jpg类型的图片!', 'info');
			    return;
			}   
			var reader = new FileReader();  
			reader.onload = function (e) {   
				document.getElementById(img).src = e.target.result;
			}; 
			reader.readAsDataURL(input.files[0]);  
		} 
	}  
	else {  
		alert("Not supported by your browser!");  
	}
	 
}

function showhide(a,b){
	document.getElementById(a).style.display = 'block';
	document.getElementById(b).style.display = 'none';
}



</script>
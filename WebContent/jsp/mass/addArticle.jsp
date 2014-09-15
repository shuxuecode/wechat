<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/ueditor/imp.jsp"%>
<div style="border:0px solid;">

 <table id="add_news_table" style="width: 600px;margin-left: 90px;" border="0">
	<tr>
		<td align="left">
			<label class="">
				标题:
			</label>
			<br>
			<input id="title" name="title" type="text" placeholder="填写标题" style="width: 300px; height: 30px;" />
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<label class="" >
				作者<span style="font-size:13px; color:#8d8d8d;">(选填)</span>
			</label>
			<br>
			<input id="author" name="author" type="text" style="width: 300px; height: 30px;" />
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<label class="">
				封面
				<span style="font-size:13px; color:#8d8d8d;">
				(大图片建议尺寸 : 900像素*500像素)(小图片建议尺寸 : 200像素*200像素)(图片大小不要超过64KB,且为jpg格式)
				</span>
			</label>
			<br>
			<span class="" style="float:left;margin:78px 0 0 30px;">
				<label for="file" style="padding: 8px;cursor: pointer;color: #fff;background-color: #428bca;border-color: #357ebd;">浏览</label>
				<br>
				<input type='file' name="coverimg" id="file" onchange="readURL(this,'img_prev');" style="display:none;" accept="image/jpeg"/>  
			</span>
			<img id="img_prev" src="http://bcs.duapp.com/wechatimages/image/coverdefault.jpg" width="285px" height="160px" style="float:left; margin:15px 0 0 170px;"/>
				
				<input id="imagePath" name="" type="hidden" nullmsg="请添加图片">
		</td>
	</tr>
	
	<tr>
		<td align="left" style="padding-left: 30px; color: #666; font-size: 14px; ">
			<br>
			<input type="checkbox" id="showcover">
			<label for="showcover" style=" cursor: pointer;">将封面图片显示在正文中</label>
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<label class="" >
				描述<span style="font-size:13px; color:#8d8d8d;">(选填)</span>
			</label>
			<br>
			<textarea name="digest" id="" cols="60" rows="2" style="resize:none;"></textarea>
		</td>
	</tr>

	<tr>
		<td align="left"><br>
			<label class="">
				正文
			</label>
			<br>
			<!-- 加载编辑器的容器 -->
    		<script type="text/plain" id="myEditor" name="content">
    		</script>
    		<script type="text/javascript">
		     //初始化
		       //var ue = UE.getEditor('myEditor');
		       UE.getEditor('myEditor');
       		</script>
		</td>
	</tr>

	<tr>
		<td align="left">
			<br>
			<div id="link1" style="display:block;">
				<a href="javascript:void(0);" onclick="showhide('link2','link1');">添加原文链接</a>
			</div>
			<div id="link2" style="display:none;">
				<label class="">
					原文链接
				</label>
				<br>
				<input id="url" name="url" type="text" placeholder="填写链接地址" style="width: 440px"/>
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
			<input id="sort" name="sort" type="number" min="1" placeholder="选择显示顺序" style="width:120px;"/>
		</td>
	</tr>

</table>


</div>

<script type="text/javascript">
//HTML5
function readURL(input,img) {  
	if(window.FileReader) {  
		if (input.files && input.files[0]) { 
			//判断图片的格式——jpg
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
			//限制图片的大小
			if(input.files[0].size > 64*1024){
				$.messager.alert('提示', '图片超过了64kb的大小限制', 'info');
				this.value = "";
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
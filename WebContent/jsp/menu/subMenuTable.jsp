<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>	

        <div id="" style="border: 0px solid; width: 570px; height: 350px; margin-left: 5px;">
            <div style="border-bottom: 1px solid #EBEBEB; padding: 20px 20px; background-color: #f7f7f7;">
                编辑子菜单 (<%=request.getParameter("name") %>)
            </div>
            <div style="margin-top: 20px;">
                <table border=0 style="margin-left: 20px">
                    <tr style="display: block;">
                        <td style="border: 0px solid red; width: 130px; height: 50px;">
                            菜单名称:
                        </td>
                        <td style="border: 0px solid red; width: 200px; height: 50px;">
                            <input type="text" style="width: 190px; height: 26px;"
                            	id="menu" name="" value="<%=request.getParameter("name") %>"/>
                        </td>
                        <td>
                        </td>
                    </tr>
                    <tr style="display: block;">
                        <td style="border: 0px solid red; width: 130px; height: 50px;">
                            	点击菜单触发:
                        </td>
                        <td style="border: 0px solid red; width: 200px; height: 50px;">
                            <select id="mySelect" onchange="menuChange()" style="width: 190px; height: 26px; padding-left: 10px;">
                                <option value="null" onclick="" selected>无触发</option>
                                <option value="keyword" onclick="">关键词</option>
                                <option value="website" onclick="">微网站页面</option>
                                <option value="url" onclick="">指定网址</option>
                            </select>
                        </td>
                        <td>
                        </td>
                    </tr>
                    <tr id="a" style="display: none;">
                        <td style="border: 0px solid red; width: 130px; height: 50px;">
                            关键词:
                        </td>
                        <td style="border: 0px solid red; width: 200px; height: 50px;">
                            <input id="key_word" type="text" style="width: 190px; height: 26px;" placeholder="尚未选择">
                            <input id="key_word_id" type="hidden" >
                        </td>
                        <td>
                            <input type="button" value="选择" onclick="$('#select_keyword').window('open');">
                        </td>
                    </tr>
                    <tr id="b" style="display: none;">
                        <td style="border: 0px solid red; width: 130px; height: 60px;">
                            微网站页面:
                        </td>
                        <td style="border: 0px solid red; width: 200px; height: 60px;">
                            <div id="web_site" style="display:none; margin-bottom: 10px;">
                            	<span id="web_site_name" class=""></span>
                            	<a id="web_site_id" herf="#" style="text-decoration: underline;color: #369;">查看</a>
                            </div>
                            <span class="">
                                <input type="button" value="选择内容页">
                            </span>
                            <span class="">
                                <input type="button" value="选择栏目页">
                            </span>
                        </td>
                        <td>
                        </td>
                    </tr>
                    
                    <tr id="c" style="display: none;">
                        <td style="border: 0px solid red; width: 130px; height: 50px;">
                            指定网址:
                        </td>
                        <td colspan="2" style="border: 0px solid red; width: 200px; height: 50px;">
                            <input id="url" type="text" style="width: 245px; height: 26px;" name="" placeholder="以http://开头的网址">
                        </td>
                    </tr>
                </table>
                <div id="upd" style="width: 580px; height: 50px; display:none;
                margin-top: 20px; border-top: 1px solid #EBEBEB;">
                    <input type="button" style="height: 40px; width: 80px; 
                    margin: 10px 0 0 240px; background-color: #0076CC;"
                    value="确认" onclick="updMenu()">
                </div>
                <div id="add" style=" width:580px; height:50px; display:none;
			        margin-top:20px; border-top:1px solid #EBEBEB;">
		            <input type="button" style="height:40px; width:80px; 
		            margin:10px 0 0 240px; background-color:#0076CC;" 
		            value="确认" onclick="addMenu('<%=request.getParameter("id") %>')">
			    </div>
            </div>
        </div>
        <input type="hidden" id="menuid" value="<%=request.getParameter("id") %>">
		<input type="hidden" id="menuType" value="<%=request.getParameter("type") %>">
		<input type="hidden" id="menuParam1" value="<%=request.getParameter("param1") %>">
		<input type="hidden" id="menuParam2" value="<%=request.getParameter("param2") %>">
		
		
		
		<input type="hidden" id="newOrUpd" value="<%=request.getParameter("newOrUpd") %>">
        
        <script type="text/javascript">
            $(function() {
            	var x = document.getElementById("mySelect");
               	if($('#menuType').val() == '0'){

                   }
               	else if($('#menuType').val() == '1'){
               		x.options[1].selected = true ;
               		$('#key_word').val($('#menuParam2').val());	
               		document.getElementById('a').style.display = 'block';
               		
                   }
           		else if($('#menuType').val() == '2'){
           			x.options[2].selected = true ;//web_site_name
           			document.getElementById('web_site_name').innerHTML = $('#menuParam1').val();
           			document.getElementById('web_site_id').innerHTML = $('#menuParam2').val();
           			document.getElementById('web_site').style.display = 'block';
           			document.getElementById('b').style.display = 'block';
                   }
           		else if($('#menuType').val() == '3'){
           			x.options[3].selected = true ;
           			$('#url').val($('#menuParam1').val());	
           			document.getElementById('c').style.display = 'block';
           		}
                

                ($('#newOrUpd').val() == 'new') ? document.getElementById('add').style.display = 'block' : document.getElementById('upd').style.display = 'block';
            });

            
        </script>
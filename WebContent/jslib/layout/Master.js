// JavaScript Document
function switchTag(tag, theUrl) {
	var obj = document.getElementById(tag);
    var lis = document.getElementById(tag).parentNode.getElementsByTagName('li');
	var iframe = document.getElementById("iFrame");
	iframe.src = theUrl;
	
    for (var i = 0; i < lis.length; i++) {
        lis[i].getElementsByTagName("a")[0].className = "";
        lis[i].getElementsByTagName("a")[0].getElementsByTagName("span")[0].className = "";
    }
    obj.getElementsByTagName("a")[0].className = "selectli";
    obj.getElementsByTagName("a")[0].getElementsByTagName("span")[0].className = "selectspan";

    
}

$(function () {
	
	var url1 = $('#logout1').val();
	var url2 = $('#logout2').val();

	var logout = $("#logout");
	//
	logout.click(function(){
        $.post(url1,
        		function(data, state){
        	        //这里显示从服务器返回的数据
        	        console.info(data);
        	        //这里显示返回的状态
        	        console.info(state);
        	        if(state){
        	        	location.replace(url2);
        	        }
        	    }
        );
    });
	
	
		 
});
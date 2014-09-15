
$(function(){

	//创建一个div  
    var backToTop = document.createElement("div");  
    //添加到页面  
    document.body.appendChild(backToTop);
	
	backToTop.id="back_to_top";

	backToTop.style.width = '50px';
	backToTop.style.height = '50px';
	backToTop.style.position = 'fixed';
	backToTop.style.bottom = '50px';
	backToTop.style.right = '40px';
	backToTop.style.borderRadius = '10px 10px 10px 10px';
	backToTop.style.textDecoration = 'none';
	backToTop.style.textAlign = 'center';
	backToTop.style.display = 'none';
	backToTop.style.backgroundColor = 'white';
	backToTop.style.border = '2px solid #999';
	backToTop.style.cursor = 'pointer';
	
	

	backToTop.innerHTML = '<div id="bttt" style="display:block;font-size:18px;font-family: Tahoma;color:#666;line-height:45px;"title="to top">Top</div><div id="bttd" style="display:none;font-size:16px;font-family: Tahoma;color:#666;line-height:48px;"title="返回顶部">顶部</div>';

	backToTop.onmouseover = function(){
		document.getElementById('bttt').style.display='none';
		document.getElementById('bttd').style.display='block';
	}

	backToTop.onmouseout = function(){
		document.getElementById('bttt').style.display='block';
		document.getElementById('bttd').style.display='none';
	}

	// 滚动窗口来判断按钮显示或隐藏
	$(window).scroll(function() {
		if ($(this).scrollTop() > 200) {
			$('#back_to_top').fadeIn(400);
		} else {
			$('#back_to_top').stop().fadeOut(400);
		}
	});
	// jQuery实现动画滚动
	$('#back_to_top').click(function(event) {
		event.preventDefault();
		$('html, body').animate({scrollTop: 0}, 200);
	});

});
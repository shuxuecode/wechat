// JavaScript Document
$(document).ready(function() {
			// 存储变量
			var accordion_head = $('.leftmenu > li > a'),
				accordion_body = $('.leftmenu li > .sub-menu');
			// 在负载打开第一个选项卡
			accordion_head.first().addClass('select').next().slideDown('normal');
			// 点击函数
			accordion_head.on('click', function(event) {
				// 禁用标题链接
				event.preventDefault();
				// 单击显示和隐藏选项卡
				if ($(this).attr('class') != 'select'){
					accordion_body.slideUp('normal');
					$(this).next().stop(true,true).slideToggle('normal');
					accordion_head.removeClass('select');
					$(this).addClass('select');
				}
			});
});



package com.weixin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义扩展接口
 */

@Controller
@RequestMapping("query")
public class ExtendController {

//	@Autowired

	/**
	 * 车辆违章查询
	 * 调用了 车首页的接口  www.cheshouye.com/api.html
	 * @return
	 */
	@RequestMapping(value = "/violation", params="hebei")
	public ModelAndView query_hebei() {
		
		return new ModelAndView("/extend/queryViolation/hebei");
	}
	
	/**
	 * 拨打电话 —— 测试在微信自带浏览器上无效果 —— 推荐直接回复文本消息，内容为电话号码，微信会假设数字消息为电话号码
	 * @return
	 */
	@RequestMapping(value = "/call", params="10086")
	public ModelAndView call_10086() {
		
		return new ModelAndView("/extend/call/callService");
	}

}

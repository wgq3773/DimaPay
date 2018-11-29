package com.dima.pay.DimaPay.notify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dima.commons.constant.RequestUrl;

@Controller
public class ReturnController {

	/**
	 * 同步通知
	 * @return
	 */
	@RequestMapping(value = RequestUrl.RETURN_URL)
	public ModelAndView receiveRetrun(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:http://47.107.142.11:8087/contentList/");
		return mv;
	}
}

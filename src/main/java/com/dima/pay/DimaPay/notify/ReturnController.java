package com.dima.pay.DimaPay.notify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dima.commons.constant.PropertiesFilePath;
import com.dima.commons.constant.RequestUrl;
import com.dima.commons.utils.PropertiesUtils;

@Controller
public class ReturnController {

	/**
	 * 同步通知
	 * @return
	 */
	@RequestMapping(value = RequestUrl.RETURN_URL)
	public ModelAndView receiveRetrun(){
		String return_skip_url = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_return_skip_url");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:" + return_skip_url);
		return mv;
	}
}

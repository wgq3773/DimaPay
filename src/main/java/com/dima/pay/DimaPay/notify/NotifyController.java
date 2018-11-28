package com.dima.pay.DimaPay.notify;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dima.commons.constant.RequestUrl;
import com.dima.commons.utils.RequestUtils;

@Controller
public class NotifyController {
	
	private static Log log = LogFactory.getLog(NotifyController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = RequestUrl.NOTIFY_URL)
	public void receiveNotify(HttpServletRequest request, HttpServletResponse response){
		log.info("接收异步通知......");
		log.info("异步通知参数打印：" + RequestUtils.getIpAddress(request) + "---" +RequestUtils.getRequestParamts(request));
		
		Map<String, String> map = RequestUtils.reqMapToMap(request);
		log.info("map形式接收到的异步通知数据：" + map);
		
		String streamData = RequestUtils.getStreamData(request);
		log.info("数据流形式接收到的异步通知数据：" + streamData);
	}
}

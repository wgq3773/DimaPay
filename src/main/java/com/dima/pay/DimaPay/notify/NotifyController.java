package com.dima.pay.DimaPay.notify;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dima.commons.constant.RequestUrl;
import com.dima.commons.utils.RequestUtils;

@Controller
public class NotifyController {
	
	private static Logger log = LoggerFactory.getLogger(NotifyController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = RequestUrl.NOTIFY_URL)
	public void receiveNotify(HttpServletRequest request, HttpServletResponse response){
		log.info("接收异步通知......");
		log.info("异步通知参数打印：" + RequestUtils.getIpAddress(request) + "---" +RequestUtils.getRequestParamts(request));
		
		Map<String, String> map = RequestUtils.reqMapToMap(request);
		//map形式接收到的异步通知数据：{order_uid=c6c3ad3029eb4c49877505f2ea97a69a, pay_price=0.02, aoid=ffefa24447b942318708561904ab5bb7, price=0.02, sign=1dd12693c4103797f8de4d5acc451ee7, order_id=c6c3ad3029eb4c49877505f2ea97a69a}
		log.info("map形式接收到的异步通知数据：" + map);
	}
}

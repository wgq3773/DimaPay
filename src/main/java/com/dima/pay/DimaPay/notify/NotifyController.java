package com.dima.pay.DimaPay.notify;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dima.api.enums.OrderStatus;
import com.dima.commons.constant.CommonConstant;
import com.dima.commons.constant.PropertiesFilePath;
import com.dima.commons.constant.RedisKey;
import com.dima.commons.constant.RequestUrl;
import com.dima.commons.redis.impl.JedisClientPool;
import com.dima.commons.utils.PropertiesUtils;
import com.dima.commons.utils.RequestUtils;
import com.dima.commons.utils.http.HttpClientUtil;

@Controller
public class NotifyController {
	
	private static Logger log = LoggerFactory.getLogger(NotifyController.class);
	
	@Autowired
	private JedisClientPool jedisClientPool;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = RequestUrl.NOTIFY_URL)
	public ModelAndView receiveNotify(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		log.info("接收异步通知......");
		log.info("异步通知参数打印：" + RequestUtils.getIpAddress(request) + "---" +RequestUtils.getRequestParamts(request));
		
		String md5Key = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_md5_key");
		String queryUrl = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_order_query_url");
		
		Map<String, String> map = RequestUtils.reqMapToMap(request);
		String orderId = map.get("order_id");
		//map形式接收到的异步通知数据：{order_uid=c6c3ad3029eb4c49877505f2ea97a69a, pay_price=0.02, aoid=ffefa24447b942318708561904ab5bb7, price=0.02, sign=1dd12693c4103797f8de4d5acc451ee7, order_id=c6c3ad3029eb4c49877505f2ea97a69a}
		log.info("map形式接收到的异步通知数据：" + map);
		
		// 验签
		//aoid + order_id + order_uid + price + pay_price + app secret
		String validateSignData = new StringBuilder()
				.append(map.get("aoid"))
				.append(map.get("order_id"))
				.append(map.get("order_uid"))
				.append(map.get("price"))
				.append(map.get("pay_price"))
				.append(md5Key).toString();
		String validateSign = DigestUtils.md5Hex(validateSignData);
		if (StringUtils.equals(validateSign, map.get("sign"))) {
			log.info(orderId + ",验签失败,validateSign:" + validateSign);
			return mv;
		}
		
		String queryResult = HttpClientUtil.sendGet(queryUrl + map.get("aoid"), null);//{"status": "success"}
		JSONObject jsonObject = (JSONObject) JSON.parse(queryResult);
		String status = (String) jsonObject.get("status");
		if (!StringUtils.equals(status, "success")) {
			log.info(orderId + ",订单状态不成功,status:" + status);
			return mv;
		}
		
		//修改订单状态
		jedisClientPool.hset(map.get("order_uid"), RedisKey.USER_ORDER_STATUS + orderId, OrderStatus.SUCCESS.getStatus());
		jedisClientPool.hset(map.get("order_uid"), RedisKey.IF_USER_PAYED, CommonConstant.YES);
		jedisClientPool.expire(map.get("order_uid"),60 * 1);// 一分钟过期
		mv.setViewName("forward:/contentList");
		return mv;
	}
}

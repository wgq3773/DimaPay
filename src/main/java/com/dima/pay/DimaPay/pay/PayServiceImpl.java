package com.dima.pay.DimaPay.pay;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dima.api.bean.Order;
import com.dima.api.bean.PayResponse;
import com.dima.api.service.PayService;
import com.dima.commons.constant.PropertiesFilePath;
import com.dima.commons.utils.PropertiesUtils;

public class PayServiceImpl implements PayService {
	
	private static Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

	@Override
	public PayResponse pay(Order order) {
//		String pay_request_url = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_pay_request_url");
		String md5_key = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_md5_key");
		String notify_url = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_notify_url");
		String return_url = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_return_url");
		String feedback_url = PropertiesUtils.getProperty(PropertiesFilePath.PAY_CHANNEL_FILE_PATH, "bufpay_feedback_url");
		
		String orderId = order.getOrderId();
		PayResponse payResponse = new PayResponse();
		payResponse.setPayDesc("payDesc");
		payResponse.setPayStatus("payStatus");
		payResponse.setRespondCode("respondCode");
		payResponse.setRespondDesc("respondDesc");
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "orderId-" + orderId);
		params.put("pay_type", "wechat");
		params.put("price", order.getOrderMoney());
		params.put("order_id", orderId);
		params.put("order_uid", order.getOrderUserId());
		params.put("notify_url", notify_url);
		params.put("return_url", return_url);
		params.put("feedback_url", feedback_url);
		String signData = new StringBuilder()
				.append(params.get("name"))
				.append(params.get("pay_type"))
				.append(params.get("price"))
				.append(params.get("order_id"))
				.append(params.get("order_uid"))
				.append(params.get("notify_url"))
				.append(params.get("return_url"))
				.append(params.get("feedback_url"))
				.append(md5_key).toString();
		String sign = DigestUtils.md5Hex(signData);
		params.put("sign", sign);
		payResponse.setParam(params);
//		log.info(orderId + ",支付接口请求地址：" + pay_request_url);
		log.info(orderId + ",支付接口请求数据：" + params);
//		String result = HttpClientUtil.sendPost(pay_request_url, params, CommonUtils.ENCODE_UTF8);
//		log.info(orderId + ",支付接口返回数据：" + result);
		return payResponse;
	}

}
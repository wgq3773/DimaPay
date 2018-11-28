package com.dima.pay.DimaPay;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.dima.api.bean.Order;
import com.dima.commons.utils.CommonUtils;
import com.dima.commons.utils.http.HttpClientUtil;

public class TestPay {

	public static void main(String[] args) {
		Order order = new Order();
		order.setOrderId(CommonUtils.getUUID());
		order.setOrderMoney("0.02");
		order.setOrderUserId(order.getOrderId());
		String url = "https://bufpay.com/api/pay/93073";
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "orderId-" + order.getOrderId());
		params.put("pay_type", "wechat");
		params.put("price", order.getOrderMoney());
		params.put("order_id", order.getOrderId());
		params.put("order_uid", order.getOrderUserId());
		params.put("notify_url", "http://47.107.142.11:8086/notify");
		params.put("return_url", "http://47.107.142.11:8086/return");
		params.put("feedback_url", "http://47.107.142.11:8086/feedback");
		String signData = new StringBuilder().append(params.get("name")).append(params.get("pay_type"))
				.append(params.get("price")).append(params.get("order_id")).append(params.get("order_uid"))
				.append(params.get("notify_url")).append(params.get("return_url"))
				 .append(params.get("feedback_url"))
				.append("c5928827be2c4159a2f95e58aedc02fa").toString();
		System.out.println(signData);
		System.out.println(params);
		String sign = DigestUtils.md5Hex(signData);
		params.put("sign", sign);
		System.err.println(params);
//		String result = HttpClientUtil.sendPost(url, params, CommonUtils.ENCODE_UTF8);
		//{"status": "ok", "aoid": "b50b8db7fa5f4d20853074d955202f32", "price": "0.02", "feedback_url": "https://www.baidu.com", "qr": "wxp://f2f1r82wwEe-jl8AB4tGeQGF_VyBrWG8Z7R9", "qr_img": 
		// "pay_type": "wechat", "qr_price": "0.02", "cid": 1, "expires_in": 300, "return_url": "https://www.baidu.com"}
//		System.out.println(result);
	}

}

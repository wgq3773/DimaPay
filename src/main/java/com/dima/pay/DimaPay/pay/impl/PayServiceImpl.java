package com.dima.pay.DimaPay.pay.impl;

import com.dima.api.bean.Order;
import com.dima.api.bean.PayResponse;
import com.dima.api.service.PayService;

public class PayServiceImpl implements PayService {

	@Override
	public PayResponse pay(Order order) {
		PayResponse payResponse = new PayResponse();
		payResponse.setPayDesc("payDesc");
		payResponse.setPayStatus("payStatus");
		payResponse.setRespondCode("respondCode");
		payResponse.setRespondDesc("respondDesc");
		return payResponse;
	}

}

package com.dima.pay.DimaPay;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dima.commons.redis.impl.JedisClientPool;

public class TestRedis {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-redis.xml");
		JedisClientPool jedisClientPool = (JedisClientPool)context.getBean("jedisClientPool");
		String string = jedisClientPool.get("jedis");
		System.out.println(string);
	}
}

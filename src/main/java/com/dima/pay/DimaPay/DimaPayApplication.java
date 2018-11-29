package com.dima.pay.DimaPay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext-*.xml")
public class DimaPayApplication {
	
	private static Logger log = LoggerFactory.getLogger(DimaPayApplication.class);

	public static void main(String[] args) {
		log.info("DimaPayApplication开始启动............");
		SpringApplication.run(DimaPayApplication.class, args);
		log.info("DimaPayApplication启动完成!!!!!!!!!!!!");
	}
}

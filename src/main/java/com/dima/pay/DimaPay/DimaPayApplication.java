package com.dima.pay.DimaPay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@ImportResource("classpath:applicationContext-DimaPay.xml")
public class DimaPayApplication {
	
	private static Log log = LogFactory.getLog(DimaPayApplication.class);

	public static void main(String[] args) {
		log.info("DimaPayApplication开始启动............");
		SpringApplication.run(DimaPayApplication.class, args);
		log.info("DimaPayApplication启动完成!!!!!!!!!!!!");
	}
}

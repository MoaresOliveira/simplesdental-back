package com.simplesdental.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;

@EnableCaching
@SpringBootApplication
public class ProductApplication {

	private static final Logger logger = LoggerFactory.getLogger(ProductApplication.class);


	public static void main(String[] args) {
		logger.info("Initializing application");
		SpringApplication.run(ProductApplication.class, args);
	}

}
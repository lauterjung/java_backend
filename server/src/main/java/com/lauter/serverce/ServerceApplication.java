package com.lauter.serverce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ServerceApplication.class, args);
		var orderService = context.getBean(OrderService.class);
	}

}

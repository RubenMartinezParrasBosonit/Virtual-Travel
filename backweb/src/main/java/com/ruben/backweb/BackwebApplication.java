package com.ruben.backweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackwebApplication.class, args);
	}

}

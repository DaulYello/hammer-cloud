package com.fmkj.fmkjeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FmkjEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FmkjEurekaApplication.class, args);
	}
}

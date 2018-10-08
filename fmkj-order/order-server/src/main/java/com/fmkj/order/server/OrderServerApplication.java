package com.fmkj.order.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.fmkj.order.server", "com.fmkj.order.dao.domain", "com.fmkj.order.client"})
@EnableFeignClients
@MapperScan("com.fmkj.order.dao.*")
@EnableSwagger2
public class OrderServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServerApplication.class, args);
	}
}

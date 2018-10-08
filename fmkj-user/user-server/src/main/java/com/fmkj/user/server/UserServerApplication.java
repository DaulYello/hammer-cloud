package com.fmkj.user.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.fmkj.user.server","com.fmkj.user.dao.domain"})     //多模块注入
@MapperScan("com.fmkj.user.dao.*")
@EnableSwagger2
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }
}

package com.fmkj.chat.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.fmkj.chat.server", "com.fmkj.chat.dao.domain"})
@EnableFeignClients
@MapperScan("com.fmkj.chat.dao.*")
@EnableSwagger2
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }
}

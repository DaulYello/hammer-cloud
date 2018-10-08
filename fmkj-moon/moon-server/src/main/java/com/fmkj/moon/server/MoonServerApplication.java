package com.fmkj.moon.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.fmkj.moon.server", "com.fmkj.moon.dao.domain"})
@EnableFeignClients
@MapperScan("com.fmkj.moon.dao.*")
@EnableSwagger2
public class MoonServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonServerApplication.class, args);
    }
}

package com.fmkj.race.server.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：17:58 2018/9/6 0006
 * @ Description：
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("workqueue");
    }
}

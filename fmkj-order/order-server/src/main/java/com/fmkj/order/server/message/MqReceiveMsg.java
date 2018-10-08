package com.fmkj.order.server.message;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: youxun
 * @Date: 2018/8/26 16:57
 * @Description:接收MQ消息
 */
@Component
public class MqReceiveMsg {


    //1、启动时自动创建队列@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //2、 启动时自动创建队,EXchange与queue绑定
       /* @RabbitListener(bindings = @QueueBinding(
                exchange = @Exchange("myExchange"),
                key = "typeKey",
                value = @Queue("myQueue")
        ))*/

    public void process(String message){

        System.out.println("接收到MQ消息");
    }


/*
    @RabbitListener(bindings = @QueueBinding(
                exchange = @Exchange("myOrderChange"),
                key = "orderKey",
                value = @Queue("myOrderQueue")
    ))
    public void processOrder(String message){

        System.out.println("接收到MQ消息");
    }
*/


}

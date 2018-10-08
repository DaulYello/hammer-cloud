package com.fmkj.order.server.message;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: youxun
 * @Date: 2018/8/26 17:01
 * @Description:MQ发送端
 */
public class MqSendMsg {


    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(){

        amqpTemplate.convertAndSend("myQueue", "msg内容");

    }

    public void sendOrder(){
        amqpTemplate.convertAndSend("myOrderChange", "myKey","msg内容");
    }
}

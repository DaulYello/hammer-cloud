package com.fmkj.race.server.rabbitmq;
 
import java.util.HashMap;
import javax.annotation.Resource;
import com.fmkj.race.dao.domain.GcJoinactivityrecord;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author 杨胜彬
 * @comments 生产者类
 * @time 2018年9月5日
 * @developers 费马科技
 */
@Component
public class MessageProducer {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send(String message) {
		this.amqpTemplate.convertAndSend("workqueue", message);
	}
 
}
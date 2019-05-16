package com.example.springbootrabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {

	Logger logger = LoggerFactory.getLogger(MQReceiver.class);

	@RabbitListener(queues = MQConfig.STRING_DIRECT_QUEUE)
	public void receive(String msg) {
		logger.info("接收到消息：" + msg);
	}

	@RabbitListener(queues = MQConfig.STRING_TOPIC_QUEUE1)
	public void receiveQueue1(String msg) {
		logger.info("[Topic/Fanout received] " + MQConfig.STRING_TOPIC_QUEUE1 + ": " + msg);
	}

	@RabbitListener(queues = MQConfig.STRING_TOPIC_QUEUE2)
	public void receiveQueue2(String msg) {
		logger.info("[Topic/Fanout received] " + MQConfig.STRING_TOPIC_QUEUE2 + ": " + msg);
	}

	/**
	 * fanout模式下，只需监听队列，不能监听FanoutExchange
	 */
	// do nothing

	@RabbitListener(queues = MQConfig.HEADER_QUEUE)
	public void receiveHeaderQueue(byte[] msg) {
		logger.info("[Headers received] " + MQConfig.HEADER_QUEUE + ": " + new String(msg));
	}

}

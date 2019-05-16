package com.example.springbootrabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSender {

	@Autowired
	AmqpTemplate amqpTemplate;

	/**
	 * 简单直连queue模式
	 * @param msg
	 */
	public void send(String msg) {
		amqpTemplate.convertAndSend(MQConfig.STRING_DIRECT_QUEUE, msg);
	}

	/**
	 * 主题交换机绑定queue模式
	 * @param msg
	 */
	public void sendTopic(String msg) {
		amqpTemplate.convertAndSend(MQConfig.STRING_TOPIC_EXCHANGE, MQConfig.ROUNTING_KEY1, msg + "-1");
		amqpTemplate.convertAndSend(MQConfig.STRING_TOPIC_EXCHANGE, MQConfig.ROUNTING_KEY2, msg + "-2");
	}

	/**
	 * 广播交换机绑定queue模式，广泛使用
	 * @param msg
	 */
	public void sendFanoutExchange(String msg) {
		amqpTemplate.convertAndSend(MQConfig.STRING_FANOUT_EXCHANGE, "", msg);
	}

	/**
	 * 头部消息交换机queue模式，很少用
	 * @param msg
	 */
	public void sendHeader(String msg) {
		MessageProperties messageProperties = new MessageProperties();
		// 必须与头部交换机申明时的头部信息一致才能发送出去 {@link com.example.springbootrabbitmq.MQConfig.headerBinding}
		messageProperties.setHeader("header1", "value1");
		messageProperties.setHeader("header2", "value2");
		Message message = new Message(msg.getBytes(), messageProperties);
		// 注意第二个参数为空字符串，不写将导致数据发送不出去！
		amqpTemplate.convertAndSend(MQConfig.STRING_HEADERS_EXCHANGE, "", message);
	}
}

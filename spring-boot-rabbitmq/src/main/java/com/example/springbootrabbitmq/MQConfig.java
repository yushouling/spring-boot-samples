package com.example.springbootrabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     rabbitmq有4中交换机模式。
 *     交换机可以理解成具有路由表的路由程序。
 *     消息始终都是先发送到交换机，由交换级经过路由传送给队列，消费者再从队列中获取消息的！
 * </pre>
 */
@Configuration
public class MQConfig {

	/**
	 * 队列名称
	 */
	public static final String STRING_DIRECT_QUEUE = "hello-queue";
	public static final String STRING_TOPIC_QUEUE1 = "topic.queue1";
	public static final String STRING_TOPIC_QUEUE2 = "topic.queue2";
	public static final String HEADER_QUEUE = "header.queue";
	/**
	 * topic交换机名称
	 */
	public static final String STRING_TOPIC_EXCHANGE = "topicExchange";
	/**
	 *  fanout广播交换机名称
	 */
	public static final String STRING_FANOUT_EXCHANGE = "fanoutExchange";
	public static final String ROUNTING_KEY1 = "topic.key1";
	// "*"代表一个单词，"#"代表0个或多个单词
	public static final String ROUNTING_KEY2 = "topic.#";
	/**
	 * 头部消息交换机名称
	 */
	public static final String STRING_HEADERS_EXCHANGE = "headersExchange";

	/**
	 * 直连模式 Direct Exchange
	 */
	@Bean
	public Queue queue() {
		return new Queue(STRING_DIRECT_QUEUE);
	}

	/**
	 * 定义绑定的队列
	 * @return
	 */
	@Bean
	public Queue topicQueue1() {
		return new Queue(STRING_TOPIC_QUEUE1);
	}

	@Bean
	public Queue topicQueue2() {
		return new Queue(STRING_TOPIC_QUEUE2);
	}

	@Bean
	public Queue headerQueue() {
		return new Queue(HEADER_QUEUE);
	}

	/**
	 * 主题模式 Topic exchange
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(STRING_TOPIC_EXCHANGE);
	}

	@Bean
	public Binding topicBind1() {
		// 只有监听了"topic.key1"才能收到消息
		return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUNTING_KEY1);
	}

	@Bean
	public Binding topicBind2() {
		// 监听了"topic.key1", "topic.key2"的消费者都能收到消息
		return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUNTING_KEY2);
	}

	/**
	 * 广播模式 Fanout Exchange 可以发给多个Queue而没有数量限制，你只需要简单的将队列绑定到交换机上。
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(STRING_FANOUT_EXCHANGE);
	}

	/**
	 * 关联到1个或多个队列上，这样只要监听了某个队列的消费者都能收到消息
	 * @return
	 */
	@Bean
	public Binding fanoutExchangeBind1() {
		return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
	}

	@Bean
	public Binding fanoutExchangeBind2() {
		return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
	}


	/**
	 * 消息头模式 Headers exchange
	 */
	@Bean
	public HeadersExchange headersExchange() {
		return new HeadersExchange(STRING_HEADERS_EXCHANGE);
	}

	@Bean
	public Binding headerBinding() {
		Map<String, Object> headerValues = new HashMap<>();
		headerValues.put("header1", "value1");
		headerValues.put("header2", "value2");
		return BindingBuilder.bind(headerQueue()).to(headersExchange()).whereAll(headerValues).match();
	}

}

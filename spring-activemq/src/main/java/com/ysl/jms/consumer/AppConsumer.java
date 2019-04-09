package com.ysl.jms.consumer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * 消费者
 */
public class AppConsumer {
    public static void main(String[] args) {
        // 初始化，在生产者之前启动，可启动多次模拟多个消费者
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
        System.out.println("消费者启动成功，开始监听消息...");
    }
}

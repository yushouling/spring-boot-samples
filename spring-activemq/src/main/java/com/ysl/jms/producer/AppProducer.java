package com.ysl.jms.producer;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * 生产者
 */
public class AppProducer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("producer.xml");
        System.out.println("生产者启动成功，准备发送消息...");
        ProducerService producerService = applicationContext.getBean(ProducerService.class);
        for (int i = 0; i < 10; i++) {
            producerService.sendMessage("hello " + i);
        }
        // 停止连接
        applicationContext.close();
    }
}

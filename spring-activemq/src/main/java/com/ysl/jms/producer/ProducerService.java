package com.ysl.jms.producer;

public interface ProducerService {
    /**
     * 发送消息
     * @param message
     */
    void sendMessage(String message);
}

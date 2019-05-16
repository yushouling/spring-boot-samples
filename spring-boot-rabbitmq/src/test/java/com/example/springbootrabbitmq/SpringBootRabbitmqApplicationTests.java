package com.example.springbootrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRabbitmqApplicationTests {
	@Autowired MQSender mqSender;

	@Test
	public void send() {
		mqSender.send("hello rabbitmq!");
		try {
			// 保持连接
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendTopic() {
		mqSender.sendTopic("This is a rabbitmq topic test msg.");
	}

	@Test
	public void sendFanoutExchange() {
		mqSender.sendFanoutExchange("This is a rabbitmq fanout test msg.");
	}

	@Test
	public void sendHeadersExchange() {
		mqSender.sendHeader("This is a rabbitmq headers test msg.");
	}

}

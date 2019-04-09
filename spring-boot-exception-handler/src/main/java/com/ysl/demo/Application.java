package com.ysl.demo;

import com.ysl.demo.exception.OrderFailedException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class Application {

	/**
	 * 测试：http://localhost:8080/hello?id=0
	 */
	@GetMapping("/hello")
	public String hello(Model model, int id) {
		model.addAttribute("helloText", " thymeleaf. " + System.currentTimeMillis());
		System.out.println(1/id);
		return "hello";
	}

	/**
	 * 测试：http://localhost:8080/order?id=-1
	 */
	@GetMapping("/order")
	public String order(Model model, int id) {
		if(id <= 0) {
			throw new OrderFailedException("订单创建失败！");
		}
		model.addAttribute("helloText", id);
		return "hello";
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

package com.ysl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.ysl.jpa.entity.News_2018award_detail;
import com.ysl.jpa.service.HelloService;

@RestController
public class HelloController {
	
	@Autowired
	private HelloService helloService;
	
	@GetMapping("/all")
	public List all(String name, String type) {
		List list = helloService.findAll();
		return list;
	}
	
	@GetMapping("/get/{userId}")
	public List<News_2018award_detail> hello(@PathVariable("userId") String userId) {
		List<News_2018award_detail> list = helloService.queryByUser(userId);
		return list;
	}

	@GetMapping("/del/{id}")
	public void deleteById(@PathVariable Integer id) {
		helloService.deleteById(id);
	}
	
	@GetMapping("/hello")
	public ModelAndView hello() {
		return new ModelAndView("hello");
	}

}

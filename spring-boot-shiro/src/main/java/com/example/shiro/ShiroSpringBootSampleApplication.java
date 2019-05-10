package com.example.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@SpringBootApplication
public class ShiroSpringBootSampleApplication {

	private static Logger log = LoggerFactory.getLogger(ShiroSpringBootSampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ShiroSpringBootSampleApplication.class, args);
	}

	/**
	 * 未授权访问异常处理
	 * @param e
	 * @param model
	 * @return
	 */
	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleException(AuthorizationException e, Model model) {

		// you could return a 404 here instead (this is how github handles 403, so the user does NOT know there is a
		// resource at that location)
		log.debug("AuthorizationException was thrown", e);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", HttpStatus.FORBIDDEN.value());
		map.put("message", "No message available");
		model.addAttribute("errors", map);

		return "error";
	}
}

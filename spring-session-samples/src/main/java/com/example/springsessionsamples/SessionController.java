package com.example.springsessionsamples;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/session")
public class SessionController {

	@RequestMapping("/set")
	public String putIntoSession(HttpServletRequest request, @RequestParam("name") String username) {
		request.getSession().setAttribute("name", username);
		return "ok";
	}

	@RequestMapping("/get")
	public String getFromSession(HttpServletRequest request) {
		String name = (String) request.getSession().getAttribute("name");
		return name;
	}
}

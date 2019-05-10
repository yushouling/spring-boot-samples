package com.example.shiro.web;

import com.example.shiro.service.ShiroSampleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试：
 在登录之前，访问 /read 和 /write 接口都无效
 用 lisi 登录（GET http://localhost:8080/login?username=lisi&password=lisi）后，可以访问 /read，不能访问 /write
 用 zhangsan 登录（GET http://localhost:8080/login?username=zhangsan&password=zhangsan）后，/read 和 /write 都可以访问
 登出后，访问 /read 和 /write 接口都无效
 */
@RestController
public class ShiroSampleController {

	@Autowired
	private ShiroSampleService shiroSampleService;

	/**
	 * 登录
	 * @param username
	 * @param password
	 */
	@GetMapping("/login")
	public void login(String username, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);
	}

	/**
	 * 退出登录
	 */
	@GetMapping("/logout")
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}

	@GetMapping("/read")
	public String read() {
		return this.shiroSampleService.read();
	}

	@GetMapping("/write")
	public String write() {
		return this.shiroSampleService.write();
	}

	@GetMapping("/read-write")
	public String readAndWrite() {
		return this.shiroSampleService.readAndWrite();
	}
}

package com.example.springsessionsamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 启动类
 */
@SpringBootApplication
@EnableRedisHttpSession
@EnableCaching
public class BootSpringSessionSamples {
	public static void main(String[] args) {
		SpringApplication.run(BootSpringSessionSamples.class, args);
	}
}

package com.example.springbootredistest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisTestApplicationTests {

	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void zset() {
		redisTemplate.opsForZSet().add("zset001", 33, 3);
		redisTemplate.opsForZSet().add("zset001", 11, 1);
		redisTemplate.opsForZSet().add("zset001", 55, 5);
		redisTemplate.opsForZSet().add("zset001", 77, 7);

		System.out.println(redisTemplate.opsForZSet().range("zset001", 0, 2));
	}

	@Test
	public void string() {
		redisTemplate.opsForValue().set("key01", "v01", 1, TimeUnit.HOURS);
		System.out.println(redisTemplate.opsForValue().get("key01"));
	}

}

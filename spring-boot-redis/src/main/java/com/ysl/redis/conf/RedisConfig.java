package com.ysl.redis.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 */
@Configuration
public class RedisConfig {

	private final static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${spring.redis.password}")
	private String springRedisPassword;
	
	@Value("${spring.redis.host}")
	private String springRedisHost;
	
	@Value("${spring.redis.port}")
	private int springRedisPort;

	@Value("${spring.redis.database}")
	private int springRedisDB;

	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;
	
	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;
	
	@Value("${spring.redis.pool.max-active}")
	private int maxActive;
	
	@Value("${spring.redis.pool.max-wait}")
	private int maxWait;
	
	@Value("${spring.redis.timeout}")
	private int timeout;

	@Bean
	public JedisPoolConfig getRedisConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(minIdle);
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxActive);
		config.setMaxWaitMillis(maxWait);
		return config;
	}

	@Bean
	public JedisConnectionFactory getConnectionFactory() {
		//RedisSentinelConfiguration configuration = redisConnectionFactory();
		JedisConnectionFactory factory = new JedisConnectionFactory();
		JedisPoolConfig config = getRedisConfig();
		factory.setPoolConfig(config);
		factory.setHostName(springRedisHost);
		factory.setPassword(springRedisPassword);
		factory.setPort(springRedisPort);
		factory.setDatabase(springRedisDB);
		factory.setTimeout(timeout);
		return factory;
	}

	@Bean
	public RedisTemplate<?, ?> getRedisTemplate() {
		RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
		return template;
	}
}
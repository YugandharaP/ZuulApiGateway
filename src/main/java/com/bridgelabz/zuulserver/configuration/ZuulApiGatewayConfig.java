package com.bridgelabz.zuulserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.bridgelabz.zuulserver.zuulfilter.AuthenticationPreFilter;

/**
 * <p></p>
 * @author yuga
 *
 */
@Configuration
public class ZuulApiGatewayConfig {

	@Bean
	public AuthenticationPreFilter preFilter() {
		return new AuthenticationPreFilter();
	}
	
	
	/**
	 * @return jedisConFactory
	 */
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		return jedisConFactory;
	}

	/**
	 * @param <T>
	 * @return redis template
	 */
	@Bean
	public <T> RedisTemplate <String, T> redisTemplate() {
		RedisTemplate<String, T> redisTemplate = new RedisTemplate<String, T>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}
	
}

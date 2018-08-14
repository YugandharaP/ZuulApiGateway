package com.bridgelabz.zuulserver.redisservice;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.zuulserver.securityservice.JwtTokenProvider;


/**
 * @author yuga
 * @since 19/07/2018
 *        <p>
 *        <b>To store token generated at the time of login operation and
 *        provides setToken,getToken and deleteToken methoods.Redis is an Open
 *        source in-memory data structure store, used as a database, cache and
 *        message broker</b>
 *        </p>
 *
 */
@Repository
public class RedisRepositoryImplementation implements IRedisRepository{

	@Autowired
	JwtTokenProvider tokenProvider;
	
	private RedisTemplate <String, Object> redisTemplate;
	private static HashOperations<String, String, String> hashOperations;
	private static String KEY = "TOKEN";

	@Autowired
	public RedisRepositoryImplementation(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private static Logger logger = LoggerFactory.getLogger(RedisRepositoryImplementation.class);

	/**
	 * To initialize hash operations and this method MUST be invoked before the class is put into service.
	 */
	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * @param clientId
	 * @return token
	 */
	@Override
	public String getToken(String userId) {
		logger.info("Getting token from redis");
		return hashOperations.get(KEY, userId);
	}
}

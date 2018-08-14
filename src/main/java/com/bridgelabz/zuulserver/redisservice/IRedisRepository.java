package com.bridgelabz.zuulserver.redisservice;

/**
 * <p>Redis repository used to save token which is used at the time of validation of user.</p>
 * @author yuga
 *
 */
public interface IRedisRepository {
	/**
	 * @param userId
	 * @return
	 */
	public String getToken(String userId);

}

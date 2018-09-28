package com.bridgelabz.zuulserver.zuulfilter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.zuulserver.redisservice.IRedisRepository;
import com.bridgelabz.zuulserver.securityservice.JwtTokenProvider;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AuthenticationPreFilter extends ZuulFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationPreFilter.class);

	@Autowired
	private IRedisRepository iRedisRepository;

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		if(request.getRequestURI().startsWith("/zuulnotes/notes")||request.getRequestURI().contains("/user/uploadimage") 
											|| request.getRequestURI().contains("/user/useractivation")) {
			String tokenFromHeader = request.getHeader("token");

			JwtTokenProvider tokenProvider = new JwtTokenProvider();
			if(tokenFromHeader==null) {
				return null;
			}
			
			String userId = tokenProvider.parseJWT(tokenFromHeader);
			String tokenFromRedis = iRedisRepository.getToken(userId);

			if (tokenFromRedis == null) {
				return null;
			}
			else {
				
			ctx.addZuulRequestHeader("userId", userId);
			LOGGER.info("PreFilter: "
					+ String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
			}
		}
		LOGGER.info("PreFilter: "
				+ String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		return "";
	}

}

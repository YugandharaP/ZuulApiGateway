package com.bridgelabz.zuulserver.securityservice;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author yuga
 * @since 09/07/2018
 *<p><b>To generate token and provide to the user</b></p>
 */
@Service
public class JwtTokenProvider {
	final static String KEY = "yuga";
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	/**
	 * 
	 * @param jwt
	 * @return email
	 * <p><b>To get claims of the token </b></p>
	 */
	public String parseJWT(String jwtToken) {
		logger.info(jwtToken);
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwtToken)
				.getBody();
		logger.info("Subject: " + claims.getSubject());
		return claims.getSubject();
	}
}
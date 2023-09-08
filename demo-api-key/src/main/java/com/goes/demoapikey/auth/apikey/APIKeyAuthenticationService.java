package com.goes.demoapikey.auth.apikey;

import java.util.Objects;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class APIKeyAuthenticationService 
{
	
	private static final String AUTH_TOKEN_HEADER_NAME = "API-KEY";
	
	public static Authentication getAuthentication(HttpServletRequest request) 
	{
		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		String apiKeyFromDataBase = "myapikey";
		log.info("API-KEY: {}", apiKey);
		if (Objects.nonNull(apiKey) && !apiKey.equals(apiKeyFromDataBase))
			throw new BadCredentialsException("API KEY inválida.");
		else if (Objects.nonNull(apiKey) && apiKey.equals(apiKeyFromDataBase))
			return new APIKeyAuthentication(AuthorityUtils.NO_AUTHORITIES, apiKeyFromDataBase);
		else
			return null;
	}

}

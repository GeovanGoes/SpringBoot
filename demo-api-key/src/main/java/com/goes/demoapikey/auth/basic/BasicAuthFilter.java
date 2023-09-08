package com.goes.demoapikey.auth.basic;

import java.io.IOException;
import java.util.Objects;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BasicAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private BasicAuthenticationService basicAuthenticationService;
	
	private static final CharSequence BASIC_PREFIX = "Basic ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String attribute = (String) request.getHeader("Authorization");
		
		if (Objects.nonNull(attribute) && attribute.contains(BASIC_PREFIX)) {
			String basicHeader = attribute.replace(BASIC_PREFIX, "");
			String string = new String(Base64.decodeBase64(basicHeader));
			log.info("Basic header: {}", string);
			if (string.contains(":")) {
				String[] splited = string.split(":");
				if (splited.length == 2) {					
					String username = splited[0];
					String password = splited[1];
					Authentication authentication = basicAuthenticationService.getAuthentication(username, password);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}

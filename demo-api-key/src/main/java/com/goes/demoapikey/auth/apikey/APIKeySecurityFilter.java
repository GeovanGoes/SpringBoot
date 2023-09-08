package com.goes.demoapikey.auth.apikey;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class APIKeySecurityFilter extends OncePerRequestFilter 
{	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try 
		{
			Authentication authentication = APIKeyAuthenticationService.getAuthentication((HttpServletRequest) request);
			if (authentication != null)
				SecurityContextHolder.getContext().setAuthentication(authentication);
		} 
		catch (Exception e) 
		{
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			PrintWriter pw = httpResponse.getWriter();
			pw.write(e.getMessage());
			pw.flush();
			pw.close();
		}
		
		filterChain.doFilter(request, response);
	}
}

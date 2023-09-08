package com.goes.demoapikey.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.goes.demoapikey.auth.apikey.APIKeyFilter;
import com.goes.demoapikey.auth.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{
	
	@Autowired
	private JwtTokenFilter tokenFilter;
	@Autowired
	private APIKeyFilter apiKeyFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
		.csrf(csrf -> csrf.disable())
		.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(ahr -> ahr.requestMatchers(HttpMethod.POST, "/login").permitAll()
				.anyRequest()
				.authenticated())
		.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(apiKeyFilter, JwtTokenFilter.class)
		.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}


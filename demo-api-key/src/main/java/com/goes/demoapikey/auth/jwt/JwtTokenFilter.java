package com.goes.demoapikey.auth.jwt;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.goes.demoapikey.domain.usuario.Usuario;
import com.goes.demoapikey.domain.usuario.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter 
{
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String attribute = (String) request.getHeader("Authorization");
		
		log.info("Authorization: {}", attribute);
		if (Objects.nonNull(attribute) && attribute.contains(BEARER_PREFIX)) {
			String token = attribute.replace(BEARER_PREFIX, "");
			String subject = tokenService.getSubject(token);
			log.info("Usuario logado username: {}", subject);
			Usuario usuario = usuarioRepository.findByUsernameAndAtivoTrue(subject);
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		
		filterChain.doFilter(request, response);
		
	}

}

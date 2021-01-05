/**
 * 
 */
package br.com.alura.Forum.config.security;

import br.com.alura.Forum.model.Usuario;
import br.com.alura.Forum.repository.UsuarioRepository;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author geovan.goes
 *
 */

public class AuthenticationTokenFilter extends OncePerRequestFilter
{
	
	TokenService tokenService;
	
	UsuarioRepository repository;
	
	/**
	 * @param tokenService
	 * @param repository 
	 */
	public AuthenticationTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		super();
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		String token = recuperarToken(request);
		boolean isValid = tokenService.isTokenValido(token);
		if (isValid)
			autenticarClient(token);
		
		filterChain.doFilter(request, response);
	}

	/**
	 * @param token
	 */
	private void autenticarClient(String token) 
	{
		Long idUsuario = tokenService.getIdUsuario(token);
		Optional<Usuario> optionalUsuario = repository.findById(idUsuario);
		
		if (optionalUsuario.isPresent())
		{
			Usuario usuario = optionalUsuario.get();
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication );
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private String recuperarToken(HttpServletRequest request) 
	{
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
			return null;
		return token.substring(7, token.length());
	}
}

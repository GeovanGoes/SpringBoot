/**
 * 
 */
package br.com.alura.Forum.config.security;

import br.com.alura.Forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author geovan.goes
 *
 */
@Service
public class TokenService {

	
	@Value("${forum.jwt.expiration}")
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;
	
	/**
	 * @param authentication
	 * @return
	 */
	public String gerarToken(Authentication authentication) {
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		
		Date expiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder().setIssuer("API do forúm da ALURA")
		.setSubject(String.valueOf(usuarioLogado.getId()))
		.setIssuedAt(hoje)
		.setExpiration(expiracao)
		.signWith(SignatureAlgorithm.HS256, this.secret)
		.compact();
	}

	/**
	 * @param token
	 * @return
	 */
	public boolean isTokenValido(String token) 
	{
		try 
		{
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	/**
	 * @param token
	 * @return
	 */
	public Long getIdUsuario(String token) 
	{
		try 
		{
			Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			return Long.parseLong(body.getSubject());
		} 
		catch (Exception e) 
		{
			return null;
		}
	}

}

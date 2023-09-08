package com.goes.demoapikey.auth.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.goes.demoapikey.domain.usuario.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String getSubject(String token) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(secret);
		    return JWT.require(algorithm)
		        .withIssuer("goes")
		        .build()
		        .verify(token)
		        .getSubject();
		} catch (JWTVerificationException exception){
		    throw new RuntimeException("Problemas para verificar o token", exception);
		}
	}
	
	public String gerarToken(Usuario usuario) {
		try {
			log.info(secret);
		    var algorithm = Algorithm.HMAC256(secret);
		    return JWT.create()
		        .withIssuer("goes")
		        .withSubject(usuario.getUsername())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(dataExpiracao())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
			throw new RuntimeException("Erro ao gerar o token", exception);
		}
	}

	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}

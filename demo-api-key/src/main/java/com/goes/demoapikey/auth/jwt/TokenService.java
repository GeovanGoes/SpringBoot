package com.goes.demoapikey.auth.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.goes.demoapikey.domain.usuario.Usuario;

@Service
public class TokenService {
	
	public void verify(String token) {
		DecodedJWT decodedJWT;
		try {
		    Algorithm algorithm = Algorithm.HMAC256("666-333");
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("goes")
		        .build();
		        
		    decodedJWT = verifier.verify(token);
		} catch (JWTVerificationException exception){
		    // Invalid signature/claims
		}
	}
	
	public String gerarToken(Usuario usuario) {
		try {
		    var algorithm = Algorithm.HMAC256("666-333");
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

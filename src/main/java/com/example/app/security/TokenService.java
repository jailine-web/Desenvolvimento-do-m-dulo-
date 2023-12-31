package com.example.app.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.app.model.entities.Login;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;

	public String gerarToken(Login usuario) {
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(usuario.getUsuario())
					.withExpiresAt(getDataDeexpiracao())				
					.sign(algoritmo);
			return token;
		}catch(JWTCreationException excessao) {
			throw new RuntimeException("Erro enquanto gera o token");
		}
	}
	
	public String validacaoDeToken(String token) {
		
		try {
			Algorithm algoritmo = Algorithm.HMAC256(secret);
			return JWT.require(algoritmo)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		}
		catch(JWTVerificationException ve) {
			return "";
		}
	}
	
	public Instant getDataDeexpiracao() {
		return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
	}
	
}

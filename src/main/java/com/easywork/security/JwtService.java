package com.easywork.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = System.getenv("JWT_SECRET");
	
	private static final long EXPIRATION_TIME = 1000 * 60 * 60;
	
	private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	public String generateToken(String username, String role) {
		
		return Jwts.builder()
				.subject(username)
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(
						new Date(System.currentTimeMillis()
								+ EXPIRATION_TIME))
				.signWith(key)
				.compact();
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		
	}
	
	public String extractUsername(String token) {
		return extractAllClaims(token)
				.getSubject();
				
	}
	public String extractRole(String token) {
		return extractAllClaims(token)
				.get("role",String.class);
	}
	public boolean isValid(String token) {
		return extractAllClaims(token)
		.getExpiration().after(new Date());
	}
	
	

}

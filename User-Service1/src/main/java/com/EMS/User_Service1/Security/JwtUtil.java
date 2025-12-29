package com.EMS.User_Service1.Security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.EMS.User_Service1.Entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	public String generateToken(User user) {
		
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("userId",user.getId())
				.claim("role",user.getRole())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()),SignatureAlgorithm.HS256)
				.compact();
				
	}

}

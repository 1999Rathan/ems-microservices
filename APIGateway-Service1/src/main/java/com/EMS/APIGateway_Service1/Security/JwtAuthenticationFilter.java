package com.EMS.APIGateway_Service1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter {

	private final JwtUtil jwtUtil;
	
	@Autowired
	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		
		if(request.getURI().getPath().contains("/users/login") || 
			request.getURI().getPath().contains("/users/register")) {
			return chain.filter(exchange);
		}
		
		String authHeader = request.getHeaders().getFirst("Authorization");
		
		if(authHeader==null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			return exchange.getResponse().setComplete();
		}
		
		String token = authHeader.substring(7);
		
		try {
			Claims claims = jwtUtil.validateToken(token);

			ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
					.header("X-User-Id", claims.get("userId").toString())
					.header("X-User-Role", claims.get("role").toString()).build();

			return chain.filter(exchange.mutate().request(modifiedRequest).build());

		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
	
	}

}

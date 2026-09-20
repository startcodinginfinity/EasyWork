package com.easywork.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.easywork.user.CustomerUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	public JwtAuthenticationFilter(JwtService jwtService, CustomerUserDetailsService customerUserDetailsService) {
		this.jwtService = jwtService;
		this.customerUserDetailsService = customerUserDetailsService;
	}

	private final CustomerUserDetailsService customerUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		 String token = authHeader.substring(7);
		 
		 if(!jwtService.isValid(token)){
			 filterChain.doFilter(request, response);
			 return;
		 }
		 String username = jwtService.extractUsername(token);
		 
		 UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
		 
		 UsernamePasswordAuthenticationToken authentication =
				 new UsernamePasswordAuthenticationToken(username, null, 
						 userDetails.getAuthorities());
		 authentication.setDetails(
				 new WebAuthenticationDetailsSource()
				 .buildDetails(request));
		 
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 
		 filterChain.doFilter(request, response);
	}

}

package com.easywork.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	private final AuthenticationManager authenticationManager;

	public AuthService(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	public LoginResponse login(LoginRequest loginRequest) {
	org.springframework.security.core.Authentication authentication=	authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()));
		var userDetails = (User) authentication.getPrincipal();
		
		String role = userDetails.getAuthorities()
				      .iterator()
				      .next().getAuthority();
		
		return new LoginResponse(
				"Login Successful",
				userDetails.getUsername(),
				role);
		
	}
	

}

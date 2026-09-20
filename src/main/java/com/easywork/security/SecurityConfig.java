package com.easywork.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.easywork.user.CustomerUserDetailsService;

@Configuration
public class SecurityConfig {

	public SecurityConfig(CustomerUserDetailsService customerUserDetailsService,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.customerUserDetailsService = customerUserDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	private final CustomerUserDetailsService customerUserDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration)
	        throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	
	@Bean
	public SecurityFilterChain chain(HttpSecurity security) {
		
		 security.csrf(csrf -> csrf.disable())
		 .userDetailsService(customerUserDetailsService)
				.authorizeHttpRequests(auth-> auth
						.requestMatchers("/api/users").permitAll()
						.requestMatchers("/api/auth/login").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthenticationFilter,
						UsernamePasswordAuthenticationFilter.class);
		
		return security.build();
		
		
	}

}

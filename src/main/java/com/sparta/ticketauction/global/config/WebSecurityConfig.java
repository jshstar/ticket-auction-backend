package com.sparta.ticketauction.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sparta.ticketauction.global.jwt.JwtAuthenticationFilter;
import com.sparta.ticketauction.global.jwt.JwtAuthorizationFilter;
import com.sparta.ticketauction.global.jwt.JwtUtil;
import com.sparta.ticketauction.global.util.LettuceUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;
	private final LettuceUtils lettuceUtils;
	private final UserDetailsService userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, lettuceUtils);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, lettuceUtils, userDetailsService);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(
				(sessionManagement) ->
					sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		http.authorizeHttpRequests(
			(request) ->
				request
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.requestMatchers(
						"/api/v1/users/signup", "/api/v1/auth/login"
					).permitAll()
					.anyRequest().authenticated()
		);

		http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

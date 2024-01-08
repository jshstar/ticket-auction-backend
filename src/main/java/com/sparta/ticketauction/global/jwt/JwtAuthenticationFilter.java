package com.sparta.ticketauction.global.jwt;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.domain.user.request.UserLoginRequest;
import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.response.ApiResponse;
import com.sparta.ticketauction.global.security.UserDetailsImpl;
import com.sparta.ticketauction.global.util.LettuceUtils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 토큰 인가")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtUtil jwtUtil;
	private final LettuceUtils lettuceUtils;
	private final ObjectMapper mapper = new ObjectMapper();

	@PostConstruct
	public void setup() {
		setFilterProcessesUrl("/api/v1/auth/login");
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException {
		try {
			UserLoginRequest req =
				new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					req.getEmail(),
					req.getPassword(),
					null
				)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new ApiException(INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException, ServletException {
		log.info("Login Success, username : {}", authResult.getName());

		String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
		Role role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getRole();

		String accessToken = jwtUtil.createAccessToken(username, role);
		String refreshToken = jwtUtil.createRefreshToken(username, role);

		lettuceUtils.save(jwtUtil.substringToken(refreshToken), username);

		response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
		response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String result = mapper.writeValueAsString(
			ApiResponse.of(SUCCESS_USER_LOGIN.getCode(), SUCCESS_USER_LOGIN.getMessage(), null));

		response.getWriter().write(result);
	}

	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException, ServletException {
		log.info("Login Fail, msg : {}", failed.getMessage());

		response.setStatus(NOT_FOUND_USER_FOR_LOGIN.getHttpStatus().value());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String result = mapper.writeValueAsString(
			ApiResponse.of(NOT_FOUND_USER_FOR_LOGIN.getCode(), NOT_FOUND_USER_FOR_LOGIN.getMessage(), null));

		response.getWriter().write(result);
	}
}

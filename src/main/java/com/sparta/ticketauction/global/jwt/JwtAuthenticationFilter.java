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
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.enums.Role;
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
				mapper.readValue(request.getInputStream(), UserLoginRequest.class);

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
	) throws ServletException, IOException {
		log.info("Login Success, username : {}", authResult.getName());

		User user = ((UserDetailsImpl)authResult.getPrincipal()).getUser();
		Role role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getRole();
		String username = user.getEmail();
		Long id = user.getId();
		String nickname = user.getNickname();

		String accessToken = jwtUtil.createAccessToken(id, username, role, nickname);
		String refreshToken = jwtUtil.createRefreshToken(id, username, role, nickname);

		lettuceUtils.save(
			JwtUtil.REFRESH_TOKEN_HEADER + " " + username,
			jwtUtil.substringToken(refreshToken),
			JwtUtil.REFRESH_TOKEN_TIME
		);

		jwtUtil.setAccessTokenInHeader(response, accessToken);
		jwtUtil.setRefreshTokenInCookie(response, refreshToken);

		response.setStatus(SUCCESS_USER_LOGIN.getHttpStatus().value());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String result = mapper.writeValueAsString(
			ApiResponse.of(SUCCESS_USER_LOGIN.getCode(), SUCCESS_USER_LOGIN.getMessage(), "{}")
		);

		response.getWriter().write(result);
	}

	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException, ServletException {
		log.info("Login Fail, msg : {}", failed.getMessage());

		throw new ApiException(NOT_FOUND_USER_FOR_LOGIN);
	}
}

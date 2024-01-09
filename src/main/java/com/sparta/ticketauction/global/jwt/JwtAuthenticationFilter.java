package com.sparta.ticketauction.global.jwt;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static com.sparta.ticketauction.global.response.SuccessCode.*;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.domain.user.request.UserLoginRequest;
import com.sparta.ticketauction.global.exception.ApiException;
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

	private static final int REFRESH_TOKEN_EXPIRATION = 60 * 60 * 24 * 30;

	private final JwtUtil jwtUtil;
	private final LettuceUtils lettuceUtils;

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
	) throws ServletException, IOException {
		log.info("Login Success, username : {}", authResult.getName());

		User user = ((UserDetailsImpl)authResult.getPrincipal()).getUser();
		Role role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getRole();
		String username = user.getEmail();
		Long id = user.getId();

		String accessToken = jwtUtil.createAccessToken(id, username, role);
		String refreshToken = jwtUtil.createRefreshToken(username, role);

		lettuceUtils.save("RefreshToken: " + username, jwtUtil.substringToken(refreshToken), REFRESH_TOKEN_EXPIRATION);

		response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
		response.addCookie(jwtUtil.setCookieWithRefreshToken(refreshToken));

		jwtUtil.setSuccessResponse(response, SUCCESS_USER_LOGIN);
	}

	@Override
	protected void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException failed
	) throws IOException, ServletException {
		log.info("Login Fail, msg : {}", failed.getMessage());

		jwtUtil.setExceptionResponse(response, new ApiException(NOT_FOUND_USER_FOR_LOGIN));
	}
}

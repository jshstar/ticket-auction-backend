package com.sparta.ticketauction.domain.user.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.sparta.ticketauction.global.exception.ApiException;
import com.sparta.ticketauction.global.exception.ErrorCode;
import com.sparta.ticketauction.global.jwt.JwtUtil;
import com.sparta.ticketauction.global.util.LettuceUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private static final String PREFIX_REFRESH_TOKEN = "RefreshToken: ";
	private static final String PREFIX_LOGOUT = "Logout: ";
	private final JwtUtil jwtUtil;
	private final LettuceUtils lettuceUtils;

	@Override
	public void logout(HttpServletRequest request) {
		String accessToken = jwtUtil.getAccessTokenFromRequestHeader(request);

		if (!jwtUtil.validateToken(accessToken)) {
			throw new ApiException(ErrorCode.INVALID_TOKEN);
		}

		Claims claims = jwtUtil.getUserInfoFromToken(accessToken);
		String username = claims.getSubject();
		Date expiration = claims.getExpiration();
		Integer remainingTime = jwtUtil.getRemainingTime(expiration);

		lettuceUtils.delete(PREFIX_REFRESH_TOKEN + username);
		lettuceUtils.save(PREFIX_LOGOUT + username, accessToken, remainingTime);
	}
}

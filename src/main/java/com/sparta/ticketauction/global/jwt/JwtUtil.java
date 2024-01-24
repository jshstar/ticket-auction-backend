package com.sparta.ticketauction.global.jwt;

import static com.sparta.ticketauction.global.exception.ErrorCode.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sparta.ticketauction.domain.user.enums.Role;
import com.sparta.ticketauction.global.exception.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

	public static final String ACCESS_TOKEN_HEADER = "Authorization";
	public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
	public static final String AUTHORIZATION_KEY = "auth";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final Long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 한 달
	private static final Long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 60분

	@Value("${jwt.secret.key}")
	private String secretKey;

	@Value("${server.host.front}")
	private String domain;

	private Key key;
	private JwtParser jwtParser;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
		jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
	}

	/* 엑세스 토큰 생성 */
	public String createAccessToken(Long id, String email, Role role, String nickname) {
		Date now = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(email)
				.claim(AUTHORIZATION_KEY, role)
				.claim("identify", id)
				.claim("nickname", nickname)
				.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME))
				.setIssuedAt(now)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/* 리프레시 토큰 생성 */
	public String createRefreshToken(Long id, String email, Role role, String nickname) {
		Date now = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(email)
				.claim(AUTHORIZATION_KEY, role)
				.claim("identify", id)
				.claim("nickname", nickname)
				.setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME))
				.setIssuedAt(now)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/* JWT 토큰 substring */
	public String substringToken(String token) {
		if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
			return token.substring(7);
		}

		throw new ApiException(INVALID_JWT_TOKEN);
	}

	/* 토큰 검증 */
	public void validateToken(String token) {
		try {
			jwtParser.parseClaimsJws(token);
		} catch (SecurityException | MalformedJwtException e) {
			throw new ApiException(INVALID_JWT_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new ApiException(EXPIRED_JWT_TOKEN);
		} catch (UnsupportedJwtException e) {
			throw new ApiException(UNSUPPORTED_JWT_TOKEN);
		} catch (IllegalArgumentException e) {
			throw new ApiException(NON_ILLEGAL_ARGUMENT_JWT_TOKEN);
		}
	}

	/* 토큰에서 사용자 정보 가져오기 */
	public Claims getUserInfoFromToken(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public String resolveAccessToken(HttpServletRequest request) {
		String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
		if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
			return accessToken.substring(7);
		}
		return null;
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return null;
		}

		String refreshToken = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(REFRESH_TOKEN_HEADER)) {
				refreshToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
				break;
			}
		}

		if (refreshToken != null && refreshToken.startsWith(BEARER_PREFIX)) {
			return substringToken(refreshToken);
		}
		return refreshToken;
	}

	public String getAccessTokenFromRequestHeader(HttpServletRequest request) {
		String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
		if (!StringUtils.hasText(accessToken)) {
			return null;
		}
		return substringToken(accessToken);
	}

	public Integer getRemainingTime(Date expiration) {
		Date now = new Date();
		return Math.toIntExact((expiration.getTime() - now.getTime()) / 60 / 1000);
	}

	public void setAccessTokenInHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(ACCESS_TOKEN_HEADER, accessToken);
	}

	public void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
		refreshToken = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8)
			.replaceAll("\\+", "%20");

		Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setDomain(domain);

		response.addCookie(cookie);
	}
}

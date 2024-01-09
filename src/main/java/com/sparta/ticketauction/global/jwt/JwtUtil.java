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

import com.sparta.ticketauction.domain.user.entity.constant.Role;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

	public static final String ACCESS_TOKEN_HEADER = "Authorization";
	public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
	public static final String AUTHORIZATION_KEY = "auth";
	public static final String BEARER_PREFIX = "Bearer ";
	private final Long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 60분
	private final Long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 한 달

	@Value("${jwt.secret.key}")
	private String secretKey;
	private Key key;
	private JwtParser jwtParser;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
		jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
	}

	/* 엑세스 토큰 생성 */
	public String createAccessToken(String username, Role role) {
		Date now = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(username)
				.claim(AUTHORIZATION_KEY, role)
				.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME))
				.setIssuedAt(now)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/* 리프레시 토큰 생성 */
	public String createRefreshToken(String username, Role role) {
		Date now = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(username)
				.claim(AUTHORIZATION_KEY, role)
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

		throw new ApiException(INVALID_TOKEN);
	}

	/* 토큰 검증 */
	public boolean validateToken(String token) {
		try {
			jwtParser.parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT Signature, 유효하지 않는 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
		}
		return false;
	}

	/* 토큰에서 사용자 정보 가져오기 */
	public Claims getUserInfoFromToken(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getUserInfoFromToken(token).getSubject();
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

		String refreshToken = "";
		for (Cookie cookie : cookies) {
			refreshToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
		}
		return substringToken(refreshToken);
	}

	public Cookie setCookieWithRefreshToken(String refreshToken) {
		refreshToken = URLEncoder.encode(refreshToken, StandardCharsets.UTF_8)
			.replaceAll("\\+", "%20");

		Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		return cookie;
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

}

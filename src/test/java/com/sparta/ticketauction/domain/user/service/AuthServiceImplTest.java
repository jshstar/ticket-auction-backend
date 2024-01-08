package com.sparta.ticketauction.domain.user.service;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.global.jwt.JwtUtil;
import com.sparta.ticketauction.global.util.LettuceUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	private final Long ACCESS_TOKEN_TIME = 1000L;
	private final Long REFRESH_TOKEN_TIME = 1000L;
	private final String secretKey = "7J6g7J2AIOyZhOyEse2VmOqzoCDrgpjshJwg7J6Q66m065Cc64ukIQ==";
	@Mock
	JwtUtil jwtUtil;
	@InjectMocks
	private AuthServiceImpl sut;
	@Mock
	private LettuceUtils lettuceUtils;

	@BeforeEach
	void beforeEach() {
		ReflectionTestUtils.setField(jwtUtil, "ACCESS_TOKEN_TIME", ACCESS_TOKEN_TIME);
		ReflectionTestUtils.setField(jwtUtil, "REFRESH_TOKEN_TIME", REFRESH_TOKEN_TIME);
		ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);

		jwtUtil.init();
	}

	@Test
	void 로그아웃_테스트() {
		// Given
		String email = "tester@gmail.com";
		Role role = Role.USER;

		String accessToken = "access-token";
		String refreshToken = "refresh-token";

		MockHttpServletRequest request = new MockHttpServletRequest();

		given(jwtUtil.getAccessTokenFromRequestHeader(any())).willReturn(accessToken);
		given(jwtUtil.validateToken(any())).willReturn(true);

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("auth", role);

		given(jwtUtil.getUserInfoFromToken(any())).willReturn(claims);
		given(jwtUtil.getRemainingTime(any())).willReturn(60);

		// When
		sut.logout(request);

		// Then
		then(lettuceUtils).should().delete(any(String.class));
		then(lettuceUtils).should().save(any(), any(), any());
	}
}
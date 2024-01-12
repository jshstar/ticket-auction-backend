package com.sparta.ticketauction.domain.user.service;

import static com.sparta.ticketauction.domain.user.util.UserUtil.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.domain.user.request.sms.UserForVerificationRequest;
import com.sparta.ticketauction.global.exception.ApiException;
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
	@Mock
	UserServiceImpl userService;
	@InjectMocks
	private AuthServiceImpl sut;
	@Mock
	private LettuceUtils lettuceUtils;

	@BeforeEach
	void beforeEach() {
		ReflectionTestUtils.setField(jwtUtil, "ACCESS_TOKEN_TIME", ACCESS_TOKEN_TIME);
		// ReflectionTestUtils.setField(jwtUtil, "REFRESH_TOKEN_TIME", REFRESH_TOKEN_TIME);
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

	@Nested
	class 휴대폰_인증_번호_발송_테스트 {

		@Test
		void 이미_존재하는_번호_실패() {
			// Given
			UserForVerificationRequest request = UserForVerificationRequest
				.builder()
				.to(TEST_PHONE_NUMBER)
				.build();

			given(userService.isExistedPhoneNumber(any())).willReturn(true);

			// When
			ApiException exception = assertThrows(
				ApiException.class,
				() -> sut.verifyPhone(request)
			);

			// Then
			assertThat(exception)
				.hasMessage(EXISTED_USER_PHONE_NUMBER.getMessage());
		}
	}

	@Nested
	class 토큰_재발급_테스트 {

		@Test
		void 성공() {
			// Given
			MockHttpServletRequest request = new MockHttpServletRequest();
			MockHttpServletResponse response = new MockHttpServletResponse();

			String refreshToken = "testrefreshtoken";
			String newAccessToken = "new-access-token";
			String newRefreshToken = "new-refresh-token";

			Claims claims = Jwts.claims().setSubject(TEST_EMAIL);
			claims.put("auth", Role.USER);
			claims.put("id", "1");

			given(jwtUtil.getUserInfoFromToken(any())).willReturn(claims);
			given(lettuceUtils.get(any())).willReturn(refreshToken);

			given(jwtUtil.createAccessToken(any(), any(), any())).willReturn(newAccessToken);
			given(jwtUtil.createRefreshToken(any(), any(), any())).willReturn(newRefreshToken);

			// When
			sut.reissue(request, response);

			// Then
			verify(jwtUtil).getUserInfoFromToken(any());
			verify(lettuceUtils).get(any());
			verify(jwtUtil).createAccessToken(any(), any(), any());
			verify(jwtUtil).createRefreshToken(any(), any(), any());
		}
	}
}
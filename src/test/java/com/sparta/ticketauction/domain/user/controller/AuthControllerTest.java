package com.sparta.ticketauction.domain.user.controller;

import static com.sparta.ticketauction.domain.user.util.UserUtil.*;
import static com.sparta.ticketauction.global.exception.ErrorCode.*;
import static com.sparta.ticketauction.global.response.SuccessCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.ticketauction.domain.user.request.UserLoginRequest;
import com.sparta.ticketauction.domain.user.util.JwtAuthenticationHelper;
import com.sparta.ticketauction.domain.user.util.UserUtil;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

	@Autowired
	JwtAuthenticationHelper helper;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional
	void 로그인_성공_테스트() throws Exception {
		// Given
		UserLoginRequest request = UserLoginRequest.builder()
			.email(UserUtil.TEST_EMAIL)
			.password(UserUtil.TEST_PASSWORD)
			.build();

		helper.createUser();

		// When
		ResultActions actions = mvc.perform(post("/api/v1/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(request))
			.accept(MediaType.APPLICATION_JSON)
		);

		// Then
		MockHttpServletResponse response = actions
			.andDo(print())
			.andReturn()
			.getResponse();

		assertThat(response.getStatus())
			.isEqualTo(SUCCESS_USER_LOGIN.getHttpStatus().value());

		assertThat(response.getHeader("Authorization"))
			.startsWith("Bearer");

		assertThat(response.getContentAsString())
			.contains(SUCCESS_USER_LOGIN.getCode())
			.contains(SUCCESS_USER_LOGIN.getMessage());

	}

	@Test
	void 로그인_실패_테스트() throws Exception {
		// Given
		UserLoginRequest request = UserLoginRequest.builder()
			.email("another@gmail.com")
			.password(UserUtil.TEST_PASSWORD)
			.build();

		helper.createUser();

		// When
		ResultActions actions = mvc.perform(post("/api/v1/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(request))
			.accept(MediaType.APPLICATION_JSON)
		);

		// Then
		MockHttpServletResponse response = actions
			.andDo(print())
			.andReturn()
			.getResponse();

		assertThat(response.getStatus())
			.isEqualTo(NOT_FOUND_USER_FOR_LOGIN.getHttpStatus().value());

		assertThat(response.getContentAsString())
			.contains(NOT_FOUND_USER_FOR_LOGIN.getCode())
			.contains(NOT_FOUND_USER_FOR_LOGIN.getMessage());
	}

	@Test
	void 관리자_로그인_테스트() throws Exception {
		// Given
		UserLoginRequest request = UserLoginRequest.builder()
			.email(ADMIN_TEST_EMAIL)
			.password(ADMIN_TEST_PASSWORD)
			.build();

		String accessToken = helper.adminLogin();

		// When
		ResultActions actions = mvc.perform(post("/api/v1/auth/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(mapper.writeValueAsString(request))
			.accept(MediaType.APPLICATION_JSON)
		);

		// Then
		MockHttpServletResponse response = actions
			.andDo(print())
			.andReturn()
			.getResponse();

		assertThat(response.getStatus())
			.isEqualTo(SUCCESS_USER_LOGIN.getHttpStatus().value());

		assertThat(response.getHeader("Authorization"))
			.startsWith("Bearer");

		assertThat(response.getContentAsString())
			.contains(SUCCESS_USER_LOGIN.getCode())
			.contains(SUCCESS_USER_LOGIN.getMessage());

		assertThat(helper.getRole(accessToken))
			.isEqualTo("ADMIN");
	}
}

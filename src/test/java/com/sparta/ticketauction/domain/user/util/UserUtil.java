package com.sparta.ticketauction.domain.user.util;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sparta.ticketauction.domain.user.entity.User;
import com.sparta.ticketauction.domain.user.entity.constant.Role;
import com.sparta.ticketauction.domain.user.request.UserCreateRequest;

public class UserUtil {

	public static final String TEST_EMAIL = "tester@gmail.com";
	public static final String TEST_PASSWORD = "test123!@#";
	public static final String TEST_NAME = "김수한";
	public static final String TEST_NICKNAME = "두루미";
	public static final String TEST_PHONE_NUMBER = "01012345678";
	public static final LocalDate TEST_BIRTH = LocalDate.of(1990, 1, 1);
	public static final String TEST_VERIFICATION_CODE = "123456";
	public static final String ADMIN_TEST_EMAIL = "admin@gmail.com";
	public static final String ADMIN_TEST_PASSWORD = "test123!@#";
	public static final String ADMIN_TEST_NAME = "김관리";
	public static final String ADMIN_TEST_NICKNAME = "관리자";
	public static final String ADMIN_TEST_PHONE_NUMBER = "01011112222";
	public static final LocalDate ADMIN_TEST_BIRTH = LocalDate.of(1997, 1, 1);
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	public static final User TEST_USER = User.builder()
		.id(1L)
		.email(TEST_EMAIL)
		.password(passwordEncoder.encode(TEST_PASSWORD))
		.name(TEST_NAME)
		.nickname(TEST_NICKNAME)
		.phoneNumber(TEST_PHONE_NUMBER)
		.birth(TEST_BIRTH)
		.role(Role.USER)
		.build();

	public static final User AMDIN_USER = User.builder()
		.id(1L)
		.email(ADMIN_TEST_EMAIL)
		.password(passwordEncoder.encode(ADMIN_TEST_PASSWORD))
		.name(ADMIN_TEST_NAME)
		.nickname(ADMIN_TEST_NICKNAME)
		.phoneNumber(ADMIN_TEST_PHONE_NUMBER)
		.birth(ADMIN_TEST_BIRTH)
		.role(Role.ADMIN)
		.build();

	public static User createUser() {
		return getUserCreateRequest().toEntity(passwordEncoder);
	}

	public static UserCreateRequest getUserCreateRequest() {
		return new UserCreateRequest(
			TEST_EMAIL,
			TEST_PASSWORD,
			TEST_NAME,
			TEST_NICKNAME,
			TEST_PHONE_NUMBER,
			TEST_BIRTH,
			TEST_VERIFICATION_CODE
		);
	}

	public static UserCreateRequest getUserCreateRequest(
		String email,
		String password,
		String name,
		String nickname,
		String phoneNumber,
		LocalDate birth,
		String verificationNumber
	) {
		return new UserCreateRequest(
			email,
			password,
			name,
			nickname,
			phoneNumber,
			birth,
			verificationNumber
		);
	}

}
